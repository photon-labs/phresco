/*
 * Copyright 2010 Class Action P/L
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
 */

lexer grammar ECMAScriptLexer;

options {filter=true;}

@lexer::header {
	package org.codehaus.mojo.jsimport;
	
	import java.io.File;

	import java.net.URI;
	import java.net.URISyntaxException;
	import java.util.ArrayList;
	import java.util.List;

	import org.apache.commons.io.FilenameUtils;
}

@members {
	private int varScopeLevel = 0;
	
	private List<String> assignedGlobalVars = new ArrayList<String>();
	private List<String> unassignedGlobalVars = new ArrayList<String>();
	
	private URI sourceBaseDirUri;
	private String sourceFilename;
	
	boolean moduleNameProvided;
	String moduleName;
	List<String> moduleDependencies = new ArrayList<String>();
	
	public class GAV {
		public String groupId;
		public String artifactId;
		
		public String toString() {
			return groupId + ":" + artifactId;
		}
	}
	
	private List<GAV> importGavs = new ArrayList<GAV>();
	
	public List<String> getAssignedGlobalVars() {
		return assignedGlobalVars;
	}
	
	public List<GAV> getImportGavs() {
		return importGavs;
	}
	
	public List<String> getUnassignedGlobalVars() {
		return unassignedGlobalVars;
	}
	
	public void setSourceFile(URI sourceBaseDirUri, String sourceFilename) {
		this.sourceBaseDirUri = sourceBaseDirUri;
		this.sourceFilename = sourceFilename;
	}
	
	private String makeRelPath(String filePath) {
		try {
			URI relUri = sourceBaseDirUri.resolve(new URI(filePath));
			return relUri.getPath();
		} catch (URISyntaxException e) {
			return filePath;
		}
	}
	
	private String normaliseModuleId(String moduleId) {
		if (moduleId.startsWith("./") || moduleId.startsWith("../")) {
			return makeRelPath(moduleId);
		} else {
			return moduleId;
		}
	}
}

EXTERNAL_VAR
    :   '/*global' WS EXTERNAL_VAR_DECL (',' WS? EXTERNAL_VAR_DECL)* '*/'
    ;

fragment EXTERNAL_VAR_DECL
    :   name=ID WS? (':' WS? ('true'|'false') WS?)? {
   			unassignedGlobalVars.add($name.text);
		}
    ;

IMPORTDOC
	:	'/**' .* IMPORT .* (IMPORT .* )* '*/'
	;
	
fragment 
IMPORT
	:	'@import' WS groupId=GAVID ':' artifactId=GAVID {
			// Note the import GAV params.
			GAV importGav = new GAV();
			importGav.groupId = $groupId.text;
			importGav.artifactId = $artifactId.text;
			importGavs.add(importGav);
		}
	;

fragment
GAVID  
	:   ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'-'|'0'..'9'|'.')*
    ;

COMMENT
	:	'/*' .* '*/'
	;
	
SL_COMMENT
	:	'//' .* '\n' 
	;

MODULE_DECL @init {
	moduleNameProvided = false;
	moduleDependencies.clear();
}	:	'define' WS? '(' (WS? MODULE_ID WS? ',')? (WS? MODULE_DEPENDENCIES WS? ',' WS?)? 'function' {
			StringBuilder builder = new StringBuilder();
			builder.append("define( ");
			builder.append("\"" + moduleName + "\",");
			builder.append(" [");
			boolean appendedOneDependency = false;
			for (String moduleDependency : moduleDependencies) {
				if (appendedOneDependency) {
					builder.append(',');
				}
				builder.append(" \"" + moduleDependency + "\"");
				appendedOneDependency = true;
			}
			builder.append(" ], ");
			builder.append("function");
			state.text = builder.toString();
		}
	;
	
REQUIRE_DECL @init {
	moduleDependencies.clear();
}	:	'require' WS? '(' WS? MODULE_DEPENDENCIES WS? ',' WS? 'function' {
			StringBuilder builder = new StringBuilder();
			builder.append("require( ");
			builder.append(" [");
			boolean appendedOneDependency = false;
			for (String moduleDependency : moduleDependencies) {
				if (appendedOneDependency) {
					builder.append(',');
				}
				builder.append(" \"" + moduleDependency + "\"");
				appendedOneDependency = true;
			}
			builder.append(" ], ");
			builder.append("function");
			state.text = builder.toString();
		}
	;
	
fragment MODULE_ID
	:	('"' | '\'') name=RELURL ('"' | '\'') {
			String relPath = normaliseModuleId($name.text);
			assignedGlobalVars.add(relPath);
			moduleName = relPath;
			moduleNameProvided = true;
		}
	;
	
fragment MODULE_DEPENDENCIES
	:	'[' WS? (MODULE_DEPENDENCY (WS? ',' WS? MODULE_DEPENDENCY)* WS?)? ']' {
			if (!moduleNameProvided) {
				String relPath = makeRelPath(FilenameUtils.getBaseName(sourceFilename));
				assignedGlobalVars.add(relPath);
				moduleName = relPath;
			}
		}
	;
	
fragment MODULE_DEPENDENCY
	:	('"' | '\'') name=RELURL ('"' | '\'') {
			String relPath = normaliseModuleId($name.text);
			unassignedGlobalVars.add(relPath);
			moduleDependencies.add(relPath);
		}
	;
		
fragment RELURL  
	:   ('a'..'z'|'A'..'Z'|'_'|'-'|'0'..'9'|'.'|'/'|'+')*
    ;

LITERAL
	:	( '/' RegexLiteral 
		| '\'' CharLiteral
		| '"' StringLiteral
		)
	;
	
fragment
RegexLiteral
    :   (   EscapeQuoteSequence
        |   ~( '\\' | '/' | '\n' )        
        )* 
        '/' 
    ;

fragment
CharLiteral
    :   (   EscapeQuoteSequence 
        |   ~( '\\' | '\'' | '\n' )
        )*
        '\''
    ; 

fragment
StringLiteral
    :   (   EscapeQuoteSequence
        |   ~( '\\' | '"' | '\n' )        
        )* 
        '"' 
    ;

fragment
EscapeQuoteSequence 
    :   '\\' .?          
	;     

ENTER_SCOPE
	:	'{' {++varScopeLevel;}
	;
	
EXIT_SCOPE
	:	'}' {--varScopeLevel;}
	;
	
WINDOW_VAR_DOT
    :   'window.' name=ID WS? '=' .* ';' {
			assignedGlobalVars.add($name.text);
		}
    ;

WINDOW_VAR_BRACKET
    :   'window[' WS? QUOTE name=ID QUOTE WS? ']' WS? '=' .* ';' {
			assignedGlobalVars.add($name.text);
		}
    ;

GLOBAL_VAR
    :   'var' WS VAR_DECL (WS? ',' WS? VAR_DECL)* ';'
    ;

fragment VAR_DECL 
    :   name=ID WS? '='? {
        	if (varScopeLevel == 0) {
    			assignedGlobalVars.add($name.text);
		    }
		}
    ;

GLOBAL_OBJECT
    :   'function' WS OBJECT_DECL
    ;

fragment OBJECT_DECL
    :   name=ID WS? '(' .* ')' {
        	if (varScopeLevel == 0) {
    			assignedGlobalVars.add($name.text);
		    }
		}
    ;

fragment
ID  :   ('a'..'z'|'A'..'Z'|'$'|'_') ('a'..'z'|'A'..'Z'|'$'|'_'|'0'..'9')*
    ;

fragment
WS  :   (' '|'\t'|'\r'|'\n')+
    ;

fragment
QUOTE : ('\''|'"')+
    ;
