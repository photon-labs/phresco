/*
 * ###
 * Phresco Service
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
/*******************************************************************************
 * Copyright (c) 2011 Photon.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Photon Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.photon.in/legal/ppl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Contributors:
 *     Photon - initial API and implementation
 ******************************************************************************/
package com.photon.phresco.service.model;

import java.io.File;

public class ArtifactInfo {

    String groupId;
    String artifact;
    String classifier;
    String pack;
    String version;
    File pomFile;

    public ArtifactInfo(String groupId, String artifact, String classifier,
            String pack, String version) {
        super();
        this.groupId = groupId;
        this.artifact = artifact;
        this.classifier = classifier;
        this.pack = pack;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getClassifier() {
        return classifier;
    }

    public String getPackage() {
        return pack;
    }

    public String getVersion() {
        return version;
    }

    public File getPomFile() {
        return pomFile;
    }

    public void setPomFile(File pomFile) {
        this.pomFile = pomFile;
    }

    @Override
    public String toString() {
        return "ArtifactInfo [groupId=" + groupId + ", artifact=" + artifact
                + ", classifier=" + classifier + ", pack=" + pack
                + ", version=" + version + "]";
    }

}
