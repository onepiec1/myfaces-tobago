package org.apache.myfaces.tobago.component;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.myfaces.tobago.context.TobagoFacesContext;
import org.apache.myfaces.tobago.event.SheetStateChangeEvent;
import org.apache.myfaces.tobago.renderkit.LayoutableRendererBase;
import org.apache.myfaces.tobago.renderkit.util.RenderUtil;
import org.apache.myfaces.tobago.util.DebugUtils;

import javax.faces.application.Application;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIOutput;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
public class ComponentUtil {

  public static final Class[] ACTION_ARGS = {};
  public static final Class[] ACTION_LISTENER_ARGS = {ActionEvent.class};
  public static final Class[] VALUE_CHANGE_LISTENER_ARGS = {ValueChangeEvent.class};
  public static final Class[] VALIDATOR_ARGS = {FacesContext.class, UIComponent.class, Object.class};

  private ComponentUtil() {
  }

  public static boolean hasErrorMessages(FacesContext context) {
    return org.apache.myfaces.tobago.util.ComponentUtil.hasErrorMessages(context);
  }

  public static boolean containsPopupActionListener(javax.faces.component.UICommand command) {
    return org.apache.myfaces.tobago.util.ComponentUtil.containsPopupActionListener(command);
  }

  public static String getFacesMessageAsString(FacesContext facesContext, UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getFacesMessageAsString(facesContext, component);
  }

  public static boolean isInPopup(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.isInPopup(component);
  }

  public static void resetPage(FacesContext context) {
    org.apache.myfaces.tobago.util.ComponentUtil.resetPage(context);
  }

  public static UIPage findPage(FacesContext context, UIComponent component) {
    return (UIPage) org.apache.myfaces.tobago.util.ComponentUtil.findPage(context, component);
  }

  public static UIPage findPage(UIComponent component) {
    return (UIPage) org.apache.myfaces.tobago.util.ComponentUtil.findPage(component);
  }

  public static void addStyles(UIComponent component, String[] styles) {
    ((TobagoFacesContext) FacesContext.getCurrentInstance()).getStyleFiles().addAll(Arrays.asList(styles));
  }

  public static void addScripts(UIComponent component, String[] scripts) {
    ((TobagoFacesContext) FacesContext.getCurrentInstance()).getScriptFiles().addAll(Arrays.asList(scripts));
  }

  public static void addOnloadCommands(UIComponent component, String[] cmds) {
    ((TobagoFacesContext) FacesContext.getCurrentInstance()).getOnloadScripts().addAll(Arrays.asList(cmds));
  }

  public static UIPage findPage(FacesContext facesContext) {
    return (UIPage) org.apache.myfaces.tobago.util.ComponentUtil.findPage(facesContext);
  }

  public static UIForm findForm(UIComponent component) {
    return (UIForm) org.apache.myfaces.tobago.util.ComponentUtil.findForm(component);
  }

  public static List<UIForm> findSubForms(UIComponent component) {
    return new ArrayList<UIForm>((List) org.apache.myfaces.tobago.util.ComponentUtil.findSubForms(component));
  }

  public static String findClientIdFor(UIComponent component, FacesContext facesContext) {
    return org.apache.myfaces.tobago.util.ComponentUtil.findClientIdFor(component, facesContext);
  }

  public static UIComponent findFor(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.findFor(component);
  }

  public static boolean isInActiveForm(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.isInActiveForm(component);
  }

  public static boolean isError(javax.faces.component.UIInput uiInput) {
    return org.apache.myfaces.tobago.util.ComponentUtil.isError(uiInput);
  }

  public static boolean isError(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.isError(component);
  }

  public static boolean isOutputOnly(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.isOutputOnly(component);
  }

  public static boolean mayValidate(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.mayValidate(component);
  }

  public static boolean mayUpdateModel(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.mayUpdateModel(component);
  }

  public static boolean getBooleanAttribute(UIComponent component, String name) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getBooleanAttribute(component, name);
  }

  public static void setRenderedPartially(org.apache.myfaces.tobago.component.UICommand command, String renderers) {
    ((SupportsRenderedPartially) command).setRenderedPartially(new String[]{renderers});
  }

  public static void setStyleClasses(UIComponent component, String styleClasses) {
    org.apache.myfaces.tobago.util.ComponentUtil.setStyleClasses(component, styleClasses);
  }

  public static void setMarkup(UIComponent markupComponent, String markup) {
    org.apache.myfaces.tobago.util.ComponentUtil.setMarkup(markupComponent, markup);
  }

  public static Object getAttribute(UIComponent component, String name) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getAttribute(component, name);
  }

  public static String getStringAttribute(UIComponent component, String name) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getStringAttribute(component, name);
  }

  public static int getIntAttribute(UIComponent component, String name) {
    return getIntAttribute(component, name, 0);
  }

  public static int getIntAttribute(UIComponent component, String name, int defaultValue) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getIntAttribute(component, name, defaultValue);
  }

  public static Character getCharacterAttribute(UIComponent component, String name) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getCharacterAttribute(component, name);
  }

  public static boolean isFacetOf(UIComponent component, UIComponent parent) {
    return org.apache.myfaces.tobago.util.ComponentUtil.isFacetOf(component, parent);
  }

  public static LayoutableRendererBase getRenderer(FacesContext facesContext, UIComponent component) {
    return (LayoutableRendererBase) org.apache.myfaces.tobago.util.ComponentUtil.getRenderer(facesContext, component);
  }

  public static LayoutableRendererBase getRenderer(FacesContext facesContext, String family, String rendererType) {
    return (LayoutableRendererBase) org.apache.myfaces.tobago.util.ComponentUtil.getRenderer(facesContext, family, rendererType);
  }

  public static String currentValue(UIComponent component) {
    return RenderUtil.currentValue(component);
  }

  public static List<SelectItem> getSelectItems(UIComponent component) {
    return RenderUtil.getSelectItems(component);
  }

  public static Object findParameter(UIComponent component, String name) {
    return org.apache.myfaces.tobago.util.ComponentUtil.findParameter(component, name);
  }

  public static String toString(UIComponent component, int offset) {
    return DebugUtils.toString(component, offset);
  }

  public static ActionListener createActionListener(String type) throws JspException {
    return org.apache.myfaces.tobago.util.ComponentUtil.createActionListener(type);
  }

  public static UIGraphic getFirstGraphicChild(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getFirstGraphicChild(component);
  }

  public static boolean isHoverEnabled(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.isHoverEnabled(component);
  }

  public static UIOutput getFirstNonGraphicChild(UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getFirstNonGraphicChild(component);
  }

  public static void setIntegerSizeProperty(UIComponent component, String name, String value) {
    org.apache.myfaces.tobago.util.ComponentUtil.setIntegerSizeProperty(component, name, value);
  }

  public static String removePx(String value) {
    return org.apache.myfaces.tobago.util.ComponentUtil.removePx(value);
  }

  public static void setIntegerProperty(UIComponent component, String name, String value) {
    if (value != null) {
      if (UIComponentTag.isValueReference(value)) {
        component.setValueBinding(name, createValueBinding(value));
      } else {
        component.getAttributes().put(name, new Integer(value));
      }
    }
  }

  public static void setBooleanProperty(UIComponent component, String name, String value) {
    if (value != null) {
      if (UIComponentTag.isValueReference(value)) {
        component.setValueBinding(name, createValueBinding(value));
      } else {
        component.getAttributes().put(name, Boolean.valueOf(value));
      }
    }
  }

  public static void setStringProperty(UIComponent component, String name, String value) {
    if (value != null) {
      if (UIComponentTag.isValueReference(value)) {
        component.setValueBinding(name, createValueBinding(value));
      } else {
        component.getAttributes().put(name, value);
      }
    }
  }

  public static void setValueForValueBinding(String name, Object value) {
    org.apache.myfaces.tobago.util.ComponentUtil.setValueForValueBinding(name, value);
  }

  public static ValueBinding createValueBinding(String value) {
    return org.apache.myfaces.tobago.util.ComponentUtil.createValueBinding(value);
  }

  public static String getValueFromEl(String script) {
    if (UIComponentTag.isValueReference(script)) {
      ValueBinding valueBinding = org.apache.myfaces.tobago.util.ComponentUtil.createValueBinding(script);
      script = (String) valueBinding.getValue(FacesContext.getCurrentInstance());
    }
    return script;
  }

  public static UIComponent createComponent(String componentType, String rendererType, String id) {
    return CreateComponentUtils.createComponent(componentType, rendererType, id);
  }

  public static UIComponent createComponent(FacesContext facesContext, String componentType, String rendererType, String id) {
    return CreateComponentUtils.createComponent(facesContext, componentType, rendererType, id);
  }

  public static UIColumn createTextColumn(String label, String sortable, String align, String value, String id) {
    return (UIColumn) CreateComponentUtils.createTextColumn(label, sortable, align, value, id);
  }

  public static UIColumn createColumn(String label, String sortable, String align, UIComponent child) {
    return (UIColumn) CreateComponentUtils.createColumn(label, sortable, align, child);
  }

  public static UIColumn createColumn(String label, String sortable, String align, UIComponent child, String id) {
    return (UIColumn) CreateComponentUtils.createColumn(label, sortable, align, child, id);
  }

  public static UIMenuSelectOne createUIMenuSelectOneFacet(FacesContext facesContext, UICommand command, String id) {
    return CreateComponentUtils.createUIMenuSelectOneFacet(facesContext, command, id);
  }

  public static boolean hasSelectedValue(List<SelectItem> items, Object value) {
    return org.apache.myfaces.tobago.util.ComponentUtil.hasSelectedValue(items, value);
  }

  public static UIComponent createUISelectBooleanFacet(FacesContext facesContext, UICommand command, String id) {
    return CreateComponentUtils.createUISelectBooleanFacet(facesContext,command,id);
  }

  public static int getIntValue(ValueBinding valueBinding) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getIntValue(valueBinding);
  }

  public static String createPickerId(FacesContext facesContext, UIComponent component, String postfix) {
    return org.apache.myfaces.tobago.util.ComponentUtil.createPickerId(facesContext, component, postfix);
  }

  public static String getComponentId(FacesContext facesContext, UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getComponentId(facesContext, component);
  }

  public static UIComponent provideLabel(FacesContext facesContext, UIComponent component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.provideLabel(facesContext, component);
  }

  public static List<SelectItem> getItemsToRender(javax.faces.component.UISelectOne component) {
    return RenderUtil.getItemsToRender(component);
  }

  public static List<SelectItem> getItemsToRender(javax.faces.component.UISelectMany component) {
    return RenderUtil.getItemsToRender(component);
  }

  public static void setValidator(EditableValueHolder editableValueHolder, String validator) {
    org.apache.myfaces.tobago.util.ComponentUtil.setValidator(editableValueHolder, validator);
  }

  public static void setConverter(ValueHolder valueHolder, String converterId) {
    org.apache.myfaces.tobago.util.ComponentUtil.setConverter(valueHolder, converterId);
  }

  public static void setAction(UICommand component, String type, String action) {
    org.apache.myfaces.tobago.util.ComponentUtil.setAction(component, action);
  }

  public static void setSuggestMethodBinding(UIInput component, String suggestMethod) {
    if (suggestMethod != null) {
      if (UIComponentTag.isValueReference(suggestMethod)) {
        final MethodBinding methodBinding = FacesContext.getCurrentInstance().getApplication()
            .createMethodBinding(suggestMethod, new Class[]{String.class});
        component.setSuggestMethod(methodBinding);
      } else {
        throw new IllegalArgumentException(
            "Must be a valueReference (suggestMethod): " + suggestMethod);
      }
    }
  }

  public static void setActionListener(ActionSource command, String actionListener) {
    org.apache.myfaces.tobago.util.ComponentUtil.setActionListener(command, actionListener);
  }

  public static void setValueChangeListener(EditableValueHolder valueHolder, String valueChangeListener) {
    org.apache.myfaces.tobago.util.ComponentUtil.setValueChangeListener(valueHolder, valueChangeListener);
  }

  public static void setSortActionListener(UIData data, String actionListener) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final Application application = facesContext.getApplication();
    if (actionListener != null) {
      if (UIComponentTag.isValueReference(actionListener)) {
        MethodBinding binding = application.createMethodBinding(
            actionListener, ACTION_LISTENER_ARGS);
        data.setSortActionListener(binding);
      } else {
        throw new IllegalArgumentException(
            "Must be a valueReference (sortActionListener): " + actionListener);
      }
    }
  }

  public static void setValueBinding(UIComponent component, String name, String state) {
    org.apache.myfaces.tobago.util.ComponentUtil.setValueBinding(component, name, state);
  }

  public static void setStateChangeListener(UIData data, String stateChangeListener) {
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final Application application = facesContext.getApplication();

    if (stateChangeListener != null) {
      if (UIComponentTag.isValueReference(stateChangeListener)) {
        Class[] arguments = {SheetStateChangeEvent.class};
        MethodBinding binding
            = application.createMethodBinding(stateChangeListener, arguments);
        data.setStateChangeListener(binding);
      } else {
        throw new IllegalArgumentException(
            "Must be a valueReference (actionListener): " + stateChangeListener);
      }
    }
  }

  public static String[] getMarkupBinding(FacesContext facesContext, SupportsMarkup component) {
    return org.apache.myfaces.tobago.util.ComponentUtil.getMarkupBinding(facesContext, component);
  }

  public static UIComponent findComponent(UIComponent from, String relativeId) {
    return org.apache.myfaces.tobago.util.ComponentUtil.findComponent(from, relativeId);
  }
}
