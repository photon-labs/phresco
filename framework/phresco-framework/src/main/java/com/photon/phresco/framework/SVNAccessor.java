/*
 * ###
 * Phresco Framework
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;

import com.google.gson.Gson;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.ProjectInfo;
import com.photon.phresco.util.FileUtil;
import com.photon.phresco.util.Utility;

public class SVNAccessor {

    private static final String PROJECT_INFO = "project.info";
    private static final String SVN_CHECKOUT_TEMP = "svn-checkout-temp";
    private static final String PHRESCO = "/.phresco";

    SVNURL svnURL = null;
    SVNClientManager cm = null;

    public SVNAccessor(String url, String username, String password) throws Exception {
        DAVRepositoryFactory.setup();
            this.svnURL = SVNURL.parseURIEncoded(url);
            DefaultSVNOptions options = new DefaultSVNOptions();
            this.cm = SVNClientManager.newInstance(options, username, password);
    }

    public ProjectInfo getProjectInfo(String revision) throws Exception {
        BufferedReader reader = null;
        File tempDir = new File(Utility.getSystemTemp(), SVN_CHECKOUT_TEMP);
        System.out.println("temp dir : SVNAccessor " +tempDir );
        try {
            SVNUpdateClient uc = cm.getUpdateClient();
            uc.doCheckout(svnURL.appendPath(PHRESCO, true),
                    tempDir, SVNRevision.UNDEFINED, SVNRevision.parse(revision),
                    SVNDepth.UNKNOWN, false);

            File dotProjectFile = new File(tempDir, PROJECT_INFO);
            if (!dotProjectFile.exists()) {
                throw new PhrescoException("Phresco Project definition not found");
            }

            reader = new BufferedReader(new FileReader(dotProjectFile));
            return new Gson().fromJson(reader, ProjectInfo.class);
        } finally {
            Utility.closeStream(reader);

            if (tempDir.exists()) {
                FileUtil.delete(tempDir);
            }
        }
    }

    public void checkout(File dstPath, String revision, boolean isRecursive) throws Exception {
            String projCode = getProjectInfo(revision).getCode();
            File projectRoot = new File(dstPath, projCode);
            if (projectRoot.exists()) {
                throw new PhrescoException("Import Fails", "Project with the code " + projCode + " already present");
            }
            SVNUpdateClient uc = cm.getUpdateClient();
            uc.doCheckout(svnURL, projectRoot, SVNRevision.UNDEFINED, SVNRevision.parse(revision),
                    SVNDepth.UNKNOWN, false);
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String svnURL = "https://insight.photoninfotech.com/svn/repos/phresco-projects/trunk/svn-import-projects/PHR_blogger/";
        File checkOutDir = new File("D:\\work\\projects\\phresco\\incubator\\svntest");
        String username = "******";
        String password = "******";

        SVNAccessor svnAccess = new SVNAccessor(svnURL, null, null);
        svnAccess.checkout(checkOutDir, "HEAD", true);
        System.out.println("Successfully checked out");
    }
}
