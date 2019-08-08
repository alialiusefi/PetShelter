package by.training.finaltask.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Tag Class for date located int the footer
 */
public class DateTag extends SimpleTagSupport {

    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.println(LocalDate.now());
    }

}
