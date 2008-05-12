package org.apache.myfaces.tobago.renderkit.html.scarborough.standard.tag;

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

import org.apache.myfaces.tobago.event.DatePickerController;
import org.apache.myfaces.tobago.event.PopupActionListener;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_LAYOUT_WIDTH;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_ACTION_ONCLICK;
import static org.apache.myfaces.tobago.TobagoConstants.FACET_PICKER_POPUP;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_HEIGHT;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_WIDTH;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_HIDDEN;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_POPUP;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_BOX;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_LABEL;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_GRID_LAYOUT;
import static org.apache.myfaces.tobago.TobagoConstants.FACET_LAYOUT;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_ROWS;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_CALENDAR;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_PANEL;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_COLUMNS;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_TIME;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_BUTTON;
import static org.apache.myfaces.tobago.TobagoConstants.RENDERER_TYPE_IMAGE;
import static org.apache.myfaces.tobago.TobagoConstants.ATTR_ALT;
import org.apache.myfaces.tobago.component.UIDatePicker;
import org.apache.myfaces.tobago.component.UIPopup;
import org.apache.myfaces.tobago.component.UIDateInput;
import org.apache.myfaces.tobago.component.UITimeInput;
import org.apache.myfaces.tobago.component.UIHiddenInput;
import org.apache.myfaces.tobago.component.CreateComponentUtils;
import org.apache.myfaces.tobago.component.AbstractUIPopup;
import org.apache.myfaces.tobago.component.UIBox;
import org.apache.myfaces.tobago.component.UIGridLayout;
import org.apache.myfaces.tobago.component.UIPanel;
import org.apache.myfaces.tobago.util.ComponentUtil;
import org.apache.myfaces.tobago.config.ThemeConfig;
import org.apache.myfaces.tobago.util.DateFormatUtils;
import org.apache.myfaces.tobago.compat.FacesUtils;
import org.apache.myfaces.tobago.TobagoConstants;
import org.apache.myfaces.tobago.renderkit.html.StyleClasses;
import org.apache.myfaces.tobago.context.TobagoFacesContext;
import org.apache.myfaces.tobago.context.ResourceManagerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.component.UICommand;
import javax.faces.component.UIGraphic;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import static javax.faces.convert.DateTimeConverter.CONVERTER_ID;
import java.io.IOException;
import java.util.Map;
import java.util.TimeZone;

/*
 * Date: 30.05.2006
 * Time: 22:21:17
 */
public class DatePickerRenderer extends LinkRenderer {
  private static final Log LOG = LogFactory.getLog(DatePickerRenderer.class);

  @Override
  public void onComponentCreated(FacesContext context, UIComponent component) {
    preparePicker(context, (UIDatePicker) component);
  }

  public void preparePicker(FacesContext facesContext, UIDatePicker link) {
      if (link.getForComponent() == null) {
        link.setFor("@auto");
      }
      link.setImmediate(true);
      String linkId = link.getId();
      UIHiddenInput hidden =
          (UIHiddenInput) CreateComponentUtils.createComponent(facesContext,
              UIHiddenInput.COMPONENT_TYPE, RENDERER_TYPE_HIDDEN);
      if (linkId != null) {
        hidden.setId(linkId + "hidden");
      } else {
        hidden.setId(facesContext.getViewRoot().createUniqueId());
      }
      link.getChildren().add(hidden);

      // create popup
      final AbstractUIPopup popup =
          (AbstractUIPopup) CreateComponentUtils.createComponent(facesContext, UIPopup.COMPONENT_TYPE,
              RENDERER_TYPE_POPUP);
      if (linkId != null) {
        popup.setId(linkId + "popup");
      } else {
        popup.setId(facesContext.getViewRoot().createUniqueId());
      }
      popup.getAttributes().put(TobagoConstants.ATTR_ZINDEX, 10);

      link.getFacets().put(FACET_PICKER_POPUP, popup);

      popup.setRendered(false);

      final UIComponent box = CreateComponentUtils.createComponent(
          facesContext, UIBox.COMPONENT_TYPE, RENDERER_TYPE_BOX);
      popup.getChildren().add(box);
      box.setId("box");
      // TODO: set string resources in renderer
      box.getAttributes().put(ATTR_LABEL, ResourceManagerUtil.getPropertyNotNull(
          facesContext, "tobago", "datePickerTitle"));
      UIComponent layout = CreateComponentUtils.createComponent(
          facesContext, UIGridLayout.COMPONENT_TYPE, RENDERER_TYPE_GRID_LAYOUT);
      box.getFacets().put(FACET_LAYOUT, layout);
      layout.setId("layout");
      layout.getAttributes().put(ATTR_ROWS, "*;fixed;fixed");

      final UIComponent calendar = CreateComponentUtils.createComponent(
          facesContext, javax.faces.component.UIOutput.COMPONENT_TYPE,
          RENDERER_TYPE_CALENDAR);

      calendar.setId("calendar");
      box.getChildren().add(calendar);

      // add time input
      final UIComponent timePanel = CreateComponentUtils.createComponent(
          facesContext, UIPanel.COMPONENT_TYPE, RENDERER_TYPE_PANEL);
      timePanel.setId("timePanel");
      box.getChildren().add(timePanel);
      layout = CreateComponentUtils.createComponent(
          facesContext, UIGridLayout.COMPONENT_TYPE, RENDERER_TYPE_GRID_LAYOUT);
      timePanel.getFacets().put(FACET_LAYOUT, layout);
      layout.setId("timePanelLayout");
      layout.getAttributes().put(ATTR_COLUMNS, "1*;fixed;1*");
      UIComponent cell = CreateComponentUtils.createComponent(
          facesContext, UIPanel.COMPONENT_TYPE, RENDERER_TYPE_PANEL);
      cell.setId("cell1");
      timePanel.getChildren().add(cell);

      final UIComponent time = CreateComponentUtils.createComponent(
          facesContext,
          UITimeInput.COMPONENT_TYPE,
          RENDERER_TYPE_TIME);
      timePanel.getChildren().add(time);
      time.setId("time");

      cell = CreateComponentUtils.createComponent(
          facesContext, UIPanel.COMPONENT_TYPE, RENDERER_TYPE_PANEL);
      cell.setId("cell2");
      timePanel.getChildren().add(cell);


      UIComponent buttonPanel = CreateComponentUtils.createComponent(
          facesContext, UIPanel.COMPONENT_TYPE, RENDERER_TYPE_PANEL);
      buttonPanel.setId("buttonPanel");
      layout = CreateComponentUtils.createComponent(
          facesContext, UIGridLayout.COMPONENT_TYPE, RENDERER_TYPE_GRID_LAYOUT);
      layout.setId("buttonPanelLayout");
      buttonPanel.getFacets().put(FACET_LAYOUT, layout);
      layout.getAttributes().put(ATTR_COLUMNS, "*;*");
//    layout.getAttributes().put(TobagoConstants.ATTR_BORDER, "1");

      box.getChildren().add(buttonPanel);

      final org.apache.myfaces.tobago.component.UICommand okButton =
          (org.apache.myfaces.tobago.component.UICommand) CreateComponentUtils.createComponent(facesContext,
              org.apache.myfaces.tobago.component.UIButtonCommand.COMPONENT_TYPE,
              RENDERER_TYPE_BUTTON);
      buttonPanel.getChildren().add(okButton);
      okButton.setId("ok" + DatePickerController.CLOSE_POPUP);
      okButton.getAttributes().put(ATTR_LABEL, ResourceManagerUtil.getPropertyNotNull(
          facesContext, "tobago", "datePickerOk"));

      final org.apache.myfaces.tobago.component.UICommand cancelButton =
          (org.apache.myfaces.tobago.component.UICommand) CreateComponentUtils.createComponent(facesContext,
              org.apache.myfaces.tobago.component.UIButtonCommand.COMPONENT_TYPE,
              RENDERER_TYPE_BUTTON);
      buttonPanel.getChildren().add(cancelButton);

      cancelButton.getAttributes().put(ATTR_LABEL, ResourceManagerUtil.getPropertyNotNull(
          facesContext, "tobago", "datePickerCancel"));
      cancelButton.setId(DatePickerController.CLOSE_POPUP);

      // create image
      UIGraphic image = (UIGraphic) CreateComponentUtils.createComponent(
          facesContext, UIGraphic.COMPONENT_TYPE, RENDERER_TYPE_IMAGE);
      image.setRendered(true);
      if (linkId != null) {
        image.setId(linkId + "image");
      } else {
        image.setId(facesContext.getViewRoot().createUniqueId());
      }
      image.setValue("image/date.gif");
      image.getAttributes().put(ATTR_ALT, ""); //TODO: i18n
      StyleClasses.ensureStyleClasses(image).addFullQualifiedClass("tobago-input-picker"); // XXX not a standard name
      link.getChildren().add(image);
    }


  public void prepareRender(FacesContext facesContext, UIComponent component) throws IOException {
    component.getAttributes().put(ATTR_LAYOUT_WIDTH, getConfiguredValue(facesContext, component, "pickerWidth"));
    if (facesContext instanceof TobagoFacesContext) {
      UIPopup popup = (UIPopup) component.getFacets().get(FACET_PICKER_POPUP);
      if (popup != null) {
        popup.getAttributes().put(ATTR_WIDTH, ThemeConfig.getValue(facesContext, component, "CalendarPopupWidth"));
        popup.getAttributes().put(ATTR_HEIGHT, ThemeConfig.getValue(facesContext, component, "CalendarPopupHeight"));
        ((TobagoFacesContext) facesContext).getPopups().add(popup);
      }
    }
    super.prepareRender(facesContext, component);
  }

  public void encodeBegin(FacesContext facesContext,
      UIComponent component) throws IOException {
    UIDatePicker link = (UIDatePicker) component;
    DatePickerController datePickerController = new DatePickerController();
    UIDateInput dateInput = (UIDateInput) link.getForComponent();
    if (dateInput == null) {
      LOG.error("No required UIDateInput component found.");
      return;
    }
    if (FacesUtils.hasValueBindingOrValueExpression(dateInput, TobagoConstants.ATTR_READONLY)) {
      FacesUtils.copyValueBindingOrValueExpression(link, TobagoConstants.ATTR_DISABLED,
          dateInput, TobagoConstants.ATTR_READONLY);
    } else {
      if (FacesUtils.hasValueBindingOrValueExpression(dateInput, TobagoConstants.ATTR_DISABLED)) {
        FacesUtils.copyValueBindingOrValueExpression(link, TobagoConstants.ATTR_DISABLED, 
            dateInput, TobagoConstants.ATTR_DISABLED);
      } else {
        link.setDisabled(dateInput.isReadonly() || dateInput.isDisabled());
      }
    }
    Map<String, Object>  attributes = link.getAttributes();
    link.setActionListener(datePickerController);

    UIComponent hidden = (UIComponent) link.getChildren().get(0);
    UIPopup popup = (UIPopup) link.getFacets().get(FACET_PICKER_POPUP);

    attributes.put(ATTR_ACTION_ONCLICK, "Tobago.openPickerPopup(event, '"
        + link.getClientId(facesContext) + "', '"
        + hidden.getClientId(facesContext) + "', '"
        + popup.getClientId(facesContext) +"')");

    Converter converter = getConverter(facesContext, dateInput);
    String converterPattern = "yyyy-MM-dd"; // from calendar.js  initCalendarParse
    if (converter instanceof DateTimeConverter) {
      converterPattern = DateFormatUtils.findPattern((DateTimeConverter) converter);
    } else {
     // LOG.warn("Converter for DateRenderer is not instance of DateTimeConverter. Using default Pattern "
      //    + converterPattern);
    }

    UICommand okButton = (UICommand) popup.findComponent("ok" + DatePickerController.CLOSE_POPUP);
    attributes = okButton.getAttributes();
    attributes.put(ATTR_ACTION_ONCLICK, "var textBox = writeIntoField2(this);Tobago.closePopup(this);textBox.focus();");
    attributes.put(TobagoConstants.ATTR_POPUP_CLOSE, "afterSubmit");

    UICommand cancelButton  = (UICommand) popup.findComponent(DatePickerController.CLOSE_POPUP);
    attributes = cancelButton.getAttributes();
    attributes.put(ATTR_ACTION_ONCLICK, "var textBox = writeIntoField2(this);Tobago.closePopup(this);textBox.focus();");
    attributes.put(TobagoConstants.ATTR_POPUP_CLOSE, "immediate");

    applyConverterPattern(facesContext, popup, converterPattern);
    
    if (!ComponentUtil.containsPopupActionListener(link)) {
      link.addActionListener(new PopupActionListener(popup.getId()));
    }
    super.encodeBegin(facesContext, component);
  }

  private void applyConverterPattern(FacesContext facesContext, UIPopup popup, String converterPattern) {
    UIComponent box = (UIComponent) popup.getChildren().get(0);
    UIComponent timePanel = box.findComponent("timePanel");
    if (converterPattern != null && (converterPattern.indexOf('h') > -1 || converterPattern.indexOf('H') > -1)) {
      UIComponent time = timePanel.findComponent("time");
      int popupHeight = ComponentUtil.getIntAttribute(popup, ATTR_HEIGHT);
      popupHeight += ThemeConfig.getValue(FacesContext.getCurrentInstance(), time, "fixedHeight");
      popup.getAttributes().put(ATTR_HEIGHT, popupHeight);
      DateTimeConverter dateTimeConverter
             = (DateTimeConverter) facesContext.getApplication().createConverter(CONVERTER_ID);
      if (converterPattern.indexOf('s') > -1) {
        dateTimeConverter.setPattern("HH:mm:ss");
      } else {
        dateTimeConverter.setPattern("HH:mm");
      }
      dateTimeConverter.setTimeZone(TimeZone.getDefault());
      ((UITimeInput) time).setConverter(dateTimeConverter);
    } else {
      timePanel.setRendered(false);
    }
  }
  
  public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
    UIDatePicker link = (UIDatePicker) component;
    UIDateInput dateInput = (UIDateInput) link.getForComponent();
    if (dateInput != null) {
      super.encodeEnd(facesContext, component);
    } else {
      LOG.error("No required UIDateInput component found.");
    }
  }
}
