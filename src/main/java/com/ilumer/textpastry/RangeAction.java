package com.ilumer.textpastry;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author banxuan
 * Date : 2022/11/3
 * Time : 13:14
 */
public class RangeAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        final CaretModel caretModel = editor.getCaretModel();
        Document document = editor.getDocument();

        String inputTxt = Messages.showInputDialog(project, "Where should the range start", "Range", Messages.getQuestionIcon());
        if (inputTxt == null) {
            return;
        }
        int start;
        try {
            start = Integer.parseInt(inputTxt);
        } catch (NumberFormatException exception) {
            // Popup Message
            return;
        }
        WriteCommandAction.runWriteCommandAction(project, () -> {
            List<Caret> carets = caretModel.getAllCarets();
            for (int i = 0; i < carets.size(); i++) {
                Caret caret = carets.get(i);
                String appendText = start + i + "";
                int end = caret.getSelectionEnd();
                document.insertString(end, appendText);
                caret.moveToOffset(end + appendText.length());
            }
        });
    }
}
