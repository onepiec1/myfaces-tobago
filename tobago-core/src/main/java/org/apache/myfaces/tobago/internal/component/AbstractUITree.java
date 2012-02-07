package org.apache.myfaces.tobago.internal.component;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.tobago.component.Attributes;
import org.apache.myfaces.tobago.internal.util.Deprecation;
import org.apache.myfaces.tobago.layout.LayoutComponent;
import org.apache.myfaces.tobago.model.MixedTreeModel;
import org.apache.myfaces.tobago.model.TreeSelectable;
import org.apache.myfaces.tobago.util.ComponentUtils;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.List;

public abstract class AbstractUITree extends AbstractUIData
// extends javax.faces.component.UIInput
    implements NamingContainer, LayoutComponent {

  private static final Log LOG = LogFactory.getLog(AbstractUITree.class);

  public static final String MESSAGE_NOT_LEAF = "tobago.tree.MESSAGE_NOT_LEAF";

  /** deprecated since XXX 1.6.0 ??? */
  @Deprecated
  public static final String SEP = "-";

  /** deprecated since XXX 1.6.0 ??? */
  @Deprecated
  public static final String SELECT_STATE = SEP + "selectState";

  /** deprecated since XXX 1.6.0 ???  */
  @Deprecated
  public static final String MARKED = "marked";

  public static final String SUFFIX_MARKED = "marked";
  public static final String SUFFIX_EXPANDED = "expanded";

  @Override
  public void processValidators(FacesContext facesContext) {
    final int last = hasRows() ? getFirst() + getRows() : Integer.MAX_VALUE;
    for (int rowIndex = getFirst(); rowIndex < last; rowIndex++) {
      setRowIndex(rowIndex);
      if (!isRowAvailable()) {
        break;
      }
      for (UIComponent child : getChildren()) {
        child.processValidators(facesContext);
      }
    }
  }

  @Override
  public void processUpdates(FacesContext facesContext) {
    final int last = hasRows() ? getFirst() + getRows() : Integer.MAX_VALUE;
    for (int rowIndex = getFirst(); rowIndex < last; rowIndex++) {
      setRowIndex(rowIndex);
      if (!isRowAvailable()) {
        break;
      }
      for (UIComponent child : getChildren()) {
        child.processUpdates(facesContext);
      }
    }
  }

  /**
   * @deprecated since XXX 1.6.0 ???
   */
  @Deprecated
  public UIComponent getRoot() {
    // find the UITreeNode in the children.
    for (UIComponent child : (List<UIComponent>) getChildren()) {
      if (child instanceof AbstractUITreeNode) {
        return child;
      }
      if (child instanceof AbstractUITreeData) {
        return child;
      }
    }
    return null;
  }

  /**
   * @deprecated Since 1.5.0.
   */
  @Deprecated
  public MixedTreeModel getModel() {
    Deprecation.LOG.error("Doesn't work anymore.");
    return null;
  }

  @Override
  public boolean getRendersChildren() {
    return true;
  }

  /**
   * Will be obsolete later when selectable has the type TreeSelectable.
   */
  public TreeSelectable getSelectableAsEnum() {
    return TreeSelectable.parse(ComponentUtils.getStringAttribute(this, Attributes.SELECTABLE));
  }

/*
  @Override
  public void processDecodes(FacesContext facesContext) {

    if (!isRendered()) {
      return;
    }

    if (ComponentUtils.isOutputOnly(this)) {
// XXX     setValid(true);
    } else {
      // in tree first decode node and than decode children

      decode(facesContext);

      for (Iterator i = getFacetsAndChildren(); i.hasNext();) {
        UIComponent uiComponent = ((UIComponent) i.next());
        uiComponent.processDecodes(facesContext);
      }
    }
  }
*/

/* XXX
  @Override
  public void validate(FacesContext context) {
*/

// todo: validate must be written new, without TreeState
/*
    if (isRequired() && getState().getSelection().size() == 0) {
      setValid(false);
      FacesMessage facesMessage = MessageUtils.createFacesMessage(context,
          UISelectOne.MESSAGE_VALUE_REQUIRED, FacesMessage.SEVERITY_ERROR);
      context.addMessage(getClientId(context), facesMessage);
    }

    String selectable = ComponentUtils.getStringAttribute(this, SELECTABLE);
    if (selectable != null && selectable.endsWith("LeafOnly")) {

      Set<DefaultMutableTreeNode> selection = getState().getSelection();

      for (DefaultMutableTreeNode node : selection) {
        if (!node.isLeaf()) {
          FacesMessage facesMessage = MessageUtils.createFacesMessage(
              context, MESSAGE_NOT_LEAF, FacesMessage.SEVERITY_ERROR);
          context.addMessage(getClientId(context), facesMessage);
          break; // don't continue iteration, no dublicate messages needed
        }
      }
    }
*/
//  call all validators
/*
    if (getValidators() != null) {
      for (Validator validator : getValidators()) {
        try {
          validator.validate(context, this, null);
        } catch (ValidatorException ve) {
          // If the validator throws an exception, we're
          // invalid, and we need to add a message
          setValid(false);
          FacesMessage message = ve.getFacesMessage();
          if (message != null) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(getClientId(context), message);
          }
        }
      }
    }
  }

  @Override
  public void updateModel(FacesContext facesContext) {
    // nothing to update for tree's
    // TODO: updating the model here and *NOT* in the decode phase
  }
*/

  public abstract Object getState();

}
