package com.myd.helloworld.chapter10.service;

import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;
import java.util.Map;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/30 17:11
 * @Description:
 */
public interface PdfExportService {
    void make(Map<String,Object> model,
              Document document,
              PdfWriter write,
              HttpServletRequest request,
              HttpServletResponse response);
}
