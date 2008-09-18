package org.apache.myfaces.tobago.internal.taglib;

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

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.tobago.OnComponentCreated;
import static org.apache.myfaces.tobago.TobagoConstants.TOBAGO_COMPONENT_CREATED;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;

public abstract class TobagoTag extends UIComponentTag {

  public int doStartTag() throws JspException {
    int result = super.doStartTag();

    UIComponent component = getComponentInstance();
    if (component instanceof OnComponentCreated
        && component.getAttributes().get(TOBAGO_COMPONENT_CREATED) == null) {
      component.getAttributes().put(TOBAGO_COMPONENT_CREATED, Boolean.TRUE);
      ((OnComponentCreated) component).onComponentCreated(getFacesContext(), getComponentInstance());
    }
    return result;
  }

  public String[] splitList(String renderers) {
    return StringUtils.split(renderers, ", ");
  }
}
