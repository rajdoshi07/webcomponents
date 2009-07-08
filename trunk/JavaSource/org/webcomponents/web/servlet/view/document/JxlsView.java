package org.webcomponents.web.servlet.view.document;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class JxlsView extends AbstractExcelView {

	private static XLSTransformer transformer = new XLSTransformer();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook template, HttpServletRequest request, HttpServletResponse response) throws Exception {
		transformer.transformWorkbook(template, model);
		OutputStream out = response.getOutputStream();
		try {
			template.write(out);
		} finally {
			out.close();
		}
		
	}
}
