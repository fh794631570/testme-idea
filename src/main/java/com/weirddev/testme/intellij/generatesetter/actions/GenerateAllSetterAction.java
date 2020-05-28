/*
 *  Copyright (c) 2017-2019, bruce.ge.
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; version 2 of
 *    the License.
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *    GNU General Public License for more details.
 *    You should have received a copy of the GNU General Public License
 *    along with this program;
 */

package com.weirddev.testme.intellij.generatesetter.actions;

import com.weirddev.testme.intellij.generatesetter.CommonConstants;
import com.weirddev.testme.intellij.generatesetter.GenerateAllHandlerAdapter;
import com.weirddev.testme.intellij.generatesetter.GetInfo;
import com.weirddev.testme.intellij.generatesetter.Parameters;
import com.weirddev.testme.intellij.generatesetter.complexreturntype.*;
import com.weirddev.testme.intellij.generatesetter.utils.PsiClassUtils;
import com.weirddev.testme.intellij.generatesetter.utils.PsiDocumentUtils;
import com.weirddev.testme.intellij.generatesetter.utils.PsiToolUtils;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by bruce.ge on 2016/12/23.
 */
public class GenerateAllSetterAction extends GenerateAllSetterBase {


    public GenerateAllSetterAction() {
        super(new GenerateAllHandlerAdapter() {
            @Override
            public boolean shouldAddDefaultValue() {
                return true;
            }
        });
    }

    @NotNull
    @Override
    public String getText() {
        return CommonConstants.GENERATE_SETTER_METHOD;
    }
}
