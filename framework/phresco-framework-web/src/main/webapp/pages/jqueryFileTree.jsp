<%--
  ###
  Framework Web Archive
  %%
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  %%
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>
<%@ page
	import="java.io.File,java.io.FilenameFilter,java.util.Arrays"%>
<%
/**
  * jQuery File Tree JSP Connector
  * Version 1.0
  * Copyright 2008 Joshua Gould
  * 21 April 2008
*/	
    String dir = request.getParameter("dir");
    String fileTypes = request.getParameter("restrictFileTypes");
    String filesOrFolders = request.getParameter("filesOrFolders");
    if (dir == null) {
    	return;
    }
	
	if (dir.charAt(dir.length()-1) == '\\') {
    	dir = dir.substring(0, dir.length()-1) + "/";
	} else if (dir.charAt(dir.length()-1) != '/') {
	    dir += "/";
	}
	
	dir = java.net.URLDecoder.decode(dir, "UTF-8");	
	fileTypes = java.net.URLDecoder.decode(fileTypes, "UTF-8");	
	filesOrFolders = java.net.URLDecoder.decode(filesOrFolders, "UTF-8");	

	final String[] includeFileTypes = fileTypes.split(",");
	
    if (new File(dir).exists()) {
		String[] files = new File(dir).list(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
				return name.charAt(0) != '.';
		    }
		});
		Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
		out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
		// All dirs
		//if(filesOrFolders.equals("Folder") || filesOrFolders.equals("All")) {
			for (String file : files) {
			    if (new File(dir, file).isDirectory()) {
					out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + dir + file + "/\">"
						+ file + "</a></li>");
			    }
			}
		//}
		// All files
		if(filesOrFolders.equals("File") || filesOrFolders.equals("All")) {
			for (String file : files) {
			    if (!new File(dir, file).isDirectory()) {
					int dotIndex = file.lastIndexOf('.');
					String ext = dotIndex > 0 ? file.substring(dotIndex + 1) : "";

					if(!fileTypes.equals("")) {
						if (!Arrays.asList(includeFileTypes).contains(ext)) {
				    		continue;
				    	}
					}
					out.print("<li class=\"file ext_" + ext + "\"><a href=\"#\" rel=\"" + dir + file + "\">"
						+ file + "</a></li>");
			    	}
			}
		}
		out.print("</ul>");
    }
%>