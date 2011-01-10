package com.loccasions.site;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loccasions.ejbiface.MediaRemote;
import com.loccasions.model.Media;

@WebServlet(name = "imageResizeServlet", urlPatterns = "/image/*")
public class ImageServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6602991648503854010L;

	private static final int DEFAULT_BUFFER_SIZE = 10240;

	// Look up media objects
	@EJB
	private MediaRemote mDB;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getParameter("id");

		// If no path, then we couldn't find the resource
		if (path == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Find the id
		int id = -1;

		try {
			String idpath = URLDecoder.decode(path, "UTF-8");
			id = Integer.parseInt(idpath);
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Try to find the image by id
		Media m = mDB.getMedia(id);

		if (m == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// Send the image
		resp.reset();
		resp.setBufferSize(DEFAULT_BUFFER_SIZE);
		resp.setContentType(m.getMime());
		String name = m.getName();
		resp.setHeader("content-disposition", "inline; filename=\"" + name
				+ "\"");

		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		// Resize the image
		File resized = File.createTempFile("resized", name.substring(name.lastIndexOf(".")));
		resizeImage(m.getInput(), resized);
		resp.setHeader("content-length", String.valueOf(resized.length()));
		
		// Dump the image to the output stream
		try {
			in = new BufferedInputStream(new FileInputStream(resized), DEFAULT_BUFFER_SIZE);
			out = new BufferedOutputStream(resp.getOutputStream(),
					DEFAULT_BUFFER_SIZE);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int len;

			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			
			resized.delete();
		} finally {
			close(in);
			close(out);
		}
		return;
	}

	private void resizeImage(InputStream inStream, File outFile) {
		try {
			BufferedImage in = ImageIO.read(inStream);
			float mod = (float)in.getWidth() / (float)in.getHeight();
			BufferedImage out = new BufferedImage((int)(128 * mod), 128,
					BufferedImage.TYPE_INT_RGB);
			
			Graphics2D g = out.createGraphics();
			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawImage(in, 0, 0, 128, 128, null);
			g.dispose();
			ImageIO.write(out, "jpeg", outFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Close a resource
	 */
	private void close(Closeable res) {
		if (res != null) {
			try {
				res.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
