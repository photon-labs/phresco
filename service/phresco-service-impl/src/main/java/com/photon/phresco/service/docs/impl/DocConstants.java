/*
 * ###
 * Phresco Service Implemenation
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
package com.photon.phresco.service.docs.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

/**
 * Documentation Constants
 *
 */
public interface DocConstants {

	Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, BaseColor.BLUE);
	Font DESC_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
	Font CATEGORY_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	Font RED_SMALL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	Font BODY_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);
	Font SMALL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

}
