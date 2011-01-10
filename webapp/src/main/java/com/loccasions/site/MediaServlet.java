package com.loccasions.site;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.net.URLDecoder;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.loccasions.ejbiface.MediaRemote;
import com.loccasions.model.Media;

@WebServlet(name = "mediaServlet", urlPatterns = "/media/*")
public class MediaServlet extends HttpServlet {
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
		resp.setHeader("content-length", String.valueOf(m.getSize()));
		String name = m.getName();
		resp.setHeader("content-disposition", "inline; filename=\"" + name + "\"");
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		
		// Dump the image to the output stream
		try {
			in = new BufferedInputStream(m.getInput(), DEFAULT_BUFFER_SIZE);
			out = new BufferedOutputStream(resp.getOutputStream(), DEFAULT_BUFFER_SIZE);
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int len;
			
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} finally {
			close(in);
			close(out);
		}
		return;
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
