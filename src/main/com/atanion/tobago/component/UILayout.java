/*
 * Copyright (c) 2004 Atanion GmbH, Germany
 * All rights reserved. Created 06.12.2004 20:49:49.
 * $Id$
 */
package com.atanion.tobago.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atanion.tobago.TobagoConstants;
import com.atanion.tobago.util.LayoutUtil;
import com.atanion.tobago.renderkit.LayoutRenderer;

import javax.faces.component.UIComponentBase;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;

public abstract class UILayout extends UIComponentBase implements TobagoConstants {

  private static final Log LOG = LogFactory.getLog(UILayout.class);

  public void layoutBegin(FacesContext facesContext, UIComponent component) {
    // prepare component to render
    prepareDimension(facesContext, component);

  }



  public static void prepareDimension(FacesContext facesContext, UIComponent component) {
//    LOG.info("prepareDimension for " + component.getClientId(facesContext) + " is " + component.getRendererType());
    setInnerWidth(facesContext, component);
    setInnerHeight(facesContext, component);
  }

  private static void setInnerWidth(FacesContext facesContext, UIComponent component) {
    Integer  layoutWidth = LayoutUtil.getLayoutWidth(component);
    if (layoutWidth != null) {
      int space = layoutWidth.intValue();
      int innerSpace
          = LayoutUtil.getInnerSpace(facesContext, component, space, true);
      component.getAttributes().put(ATTR_INNER_WIDTH, new Integer(innerSpace));
    }
  }

  private static void setInnerHeight(FacesContext facesContext, UIComponent component) {
    Integer  layoutHeight = LayoutUtil.getLayoutHeight(component);
    if (layoutHeight != null) {
      int space = layoutHeight.intValue();
      int innerSpace
          = LayoutUtil.getInnerSpace(facesContext, component, space, false);
      component.getAttributes().put(ATTR_INNER_HEIGHT, new Integer(innerSpace));
    }
  }


  public void encodeChildrenOfComponent(FacesContext facesContext, UIComponent component) throws IOException {
    ((LayoutRenderer)getRenderer(facesContext)).encodeChildrenOfComponent(facesContext, component);
  }


  public static UILayout getLayout(UIComponent component) {
    UILayout layout = (UILayout) component.getFacet(TobagoConstants.FACET_LAYOUT);
    if (layout == null) {
      layout = (UILayout) component.getFacet(TobagoConstants.FACET_LAYOUT_DEFAULT);
      if (layout == null) {
        layout = UIDefaultLayout.getInstance();
        component.getFacets().put(TobagoConstants.FACET_LAYOUT_DEFAULT, layout);
      }
    }
    return layout;
  }
}
