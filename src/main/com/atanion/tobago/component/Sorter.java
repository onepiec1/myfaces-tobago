package com.atanion.tobago.component;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atanion.tobago.model.SortableByApplication;
import com.atanion.tobago.model.SheetState;
import com.atanion.tobago.TobagoConstants;
import com.atanion.util.BeanComparator;

import javax.faces.el.MethodBinding;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodNotFoundException;
import javax.faces.el.ValueBinding;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.component.*;
import javax.faces.component.UIInput;
import javax.faces.model.DataModel;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: weber
 * Date: Mar 7, 2005
 * Time: 4:01:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sorter extends MethodBinding {

  private static final Log LOG = LogFactory.getLog(Sorter.class);

  public static final String ID_PREFIX = "sorter_";

  private UIData data;

  private int column;
  private boolean ascending;

  private Comparator comparator;

  public Sorter(UIData data) {
    this.data = data;
    column = -1;
    ascending = true;
  }

  public Object invoke(FacesContext facescontext, Object aobj[])
      throws EvaluationException, MethodNotFoundException {
    if (aobj[0] instanceof ActionEvent) {
      UICommand command = (UICommand) ((ActionEvent) aobj[0]).getSource();
      if (LOG.isDebugEnabled()) {
        LOG.debug("sorterId = " + command.getId());
      }

      Object value = data.getValue();
      if (value instanceof DataModel) {
        value = ((DataModel) value).getWrappedData();
      }


      if (value instanceof SortableByApplication
        || value instanceof List
        || value instanceof Object[]) {
        String sortProperty;

        if (command.getId() != null && command.getId().startsWith(ID_PREFIX)) {
          UIColumn uiColumn = null;
          try {
            int actualColumn =
                Integer.parseInt(
                    command.getId().substring(ID_PREFIX.length()));
            if (actualColumn == column) {
              ascending = !ascending;
            } else {
              ascending = true;
              column = actualColumn;
            }


            List columns = data.getColumns();
            uiColumn = (UIColumn) columns.get(column);
            UIComponent child = getFirstSortableChild(uiColumn.getChildren());
            if (child != null) {
              ValueBinding valueBinding = child.getValueBinding("value");
              String expressionString = valueBinding.getExpressionString();
              if (expressionString.startsWith("#{") &&
                    expressionString.endsWith("}")) {
                expressionString =
                    expressionString.substring(2,
                        expressionString.length() - 1);
              }
              String var = data.getVar();
              sortProperty = expressionString.substring(var.length() + 1);
              if (LOG.isDebugEnabled()) {
                LOG.debug("Sort property is " + sortProperty);
              }
            } else {
              LOG.error("No sortable component found!");
              removeSortableAttribute(uiColumn);
              return null;
            }
          } catch (Exception e) {
            LOG.error("Error while extracting sortMethod :" + e.getMessage(),
                e);
            if (uiColumn != null) {
              removeSortableAttribute(uiColumn);
            }
            return null;
          }
        } else {
          LOG.error(
              "Sorter.invoke() with illegal id in ActionEvent's source");
          return null;
        }

        if (value instanceof SortableByApplication) {
          ((SortableByApplication) value).sortBy(sortProperty);
        } else {
          // todo: locale / comparator parameter?
          // don't compare numbers with Collator.getInstance() comparator
//          comparator = Collator.getInstance();
          comparator = null;
          Comparator beanComparator
              = new BeanComparator(sortProperty, comparator, !ascending);
//          comparator = new RowComparator(ascending, method);

          if (value instanceof List) {
            Collections.sort((List) value, beanComparator);
          } else { // if (value instanceof Object[]) {
            Arrays.sort((Object[]) value, beanComparator);
          }
        }
        data.updateSheetState(facescontext);
      } else {  // DataModel?, ResultSet, Result or Object
        LOG.warn("Sorting not supported for type "
                   + (value != null ? value.getClass().toString() : "null"));
      }
    }
    return null;
  }

  private void removeSortableAttribute(UIColumn uiColumn) {
    LOG.warn("removing attribute sortable from column " + column);
    uiColumn.getAttributes().remove(TobagoConstants.ATTR_SORTABLE);
  }

  private UIComponent getFirstSortableChild(List children) {
    UIComponent child = null;

    for (Iterator iter = children.iterator(); iter.hasNext();) {
      child = (UIComponent) iter.next();
      if (child instanceof UICommand
        || child instanceof javax.faces.component.UIPanel) {
        child = getFirstSortableChild(child.getChildren());
      }
      if (child instanceof UISelectMany
        || child instanceof UISelectOne
        || child instanceof UISelectBoolean) {
        continue;
      } else if (child instanceof UIInput &&
        TobagoConstants.RENDERER_TYPE_HIDDEN.equals(child.getRendererType())) {
        continue;
      } else if (child instanceof UIOutput) {
        break;
      }
    }
    return child;
  }

  public Class getType(FacesContext facescontext)
      throws MethodNotFoundException {
    return String.class;
  }

  public int getColumn() {
    return column;
  }

  public boolean isAscending() {
    return ascending;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public void setAscending(boolean ascending) {
    this.ascending = ascending;
  }

  public void setStoredState(SheetState state) {
    if (state != null) {
      setColumn(state.getSortedColumn());
      setAscending(state.isAscending());
    }
  }
}

