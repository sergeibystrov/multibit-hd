package org.multibit.hd.ui.views.components.renderers;

import org.multibit.hd.core.dto.PaymentData;
import org.multibit.hd.core.dto.RAGStatus;
import org.multibit.hd.ui.MultiBitUI;
import org.multibit.hd.ui.views.components.Images;
import org.multibit.hd.ui.views.components.Labels;
import org.multibit.hd.ui.views.components.tables.PaymentTableModel;
import org.multibit.hd.ui.views.components.tables.StripedTable;
import org.multibit.hd.ui.views.fonts.AwesomeDecorator;
import org.multibit.hd.ui.views.fonts.AwesomeIcon;
import org.multibit.hd.ui.views.themes.Themes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Render a RAGStatus as an icon
 */
public class RAGStatusTableCellRenderer extends DefaultTableCellRenderer {

  private JLabel label = Labels.newBlankLabel();

  private PaymentTableModel paymentTableModel;

  public RAGStatusTableCellRenderer(PaymentTableModel paymentTableModel) {
      this.paymentTableModel = paymentTableModel;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
                                                 int column) {

    // Prepare the primary icon (used always), and an extra icon and containing panel for use as required.
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setVerticalAlignment(SwingConstants.CENTER);
    label.setOpaque(true);

    // Get the RAG (which is in the model as a RAGStatus
    if (value instanceof RAGStatus) {
      RAGStatus status = (RAGStatus) value;

      switch (status) {
        case RED:
          label.setForeground(Themes.currentTheme.dangerAlertBackground());
          AwesomeDecorator.bindIcon(AwesomeIcon.TIMES, label, false, MultiBitUI.SMALL_ICON_SIZE);
          break;
        case AMBER:
          label.setForeground(Themes.currentTheme.text());
          AwesomeDecorator.bindIcon(AwesomeIcon.EXCHANGE, label, false, MultiBitUI.SMALL_ICON_SIZE);
          break;
        case GREEN:
          label.setForeground(Themes.currentTheme.successAlertBackground());

          java.util.List<PaymentData> paymentDatas = paymentTableModel.getPaymentData();
          int modelRow = table.convertRowIndexToModel(row);
          PaymentData rowPaymentData = paymentDatas.get(modelRow);
          label.setIcon(Images.newConfirmationIcon(rowPaymentData.getDepth(), rowPaymentData.isCoinBase()));

          break;
        case PINK:
          label.setForeground(Themes.currentTheme.pendingAlertBackground().darker());
          AwesomeDecorator.bindIcon(AwesomeIcon.FILE_TEXT, label, false, MultiBitUI.SMALL_ICON_SIZE);
          break;
        default:
          // Unknown status
          throw new IllegalStateException("Unknown status " + status);
      }
    }

    if (isSelected) {
      label.setBackground(table.getSelectionBackground());
    } else {
      if (row % 2 == 1) {
        label.setBackground(StripedTable.alternateColor);
      } else {
        label.setBackground(StripedTable.rowColor);
      }
    }

    return label;
  }
}