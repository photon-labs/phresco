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
package com.photon.phresco.service.docs.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class PdfInput {

	private InputStream inputStream;
	private List<HashMap<String,Object>> bookmarks;

	public InputStream getInputStream() {
		return this.inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public List<HashMap<String, Object>> getBookmarks() {
		return this.bookmarks;
	}
	public void setBookmarks(List<HashMap<String, Object>> bookmarks) {
		this.bookmarks = bookmarks;
	}

}
