/**
 * 0. Project  : 
 *
 * 1. FileName : FileDownloadView.java
 * 2. Package : com.macpert.core.view
 * 3. Comment : 
 * 4. 작성자  : H.S Kang (at MACPERT)
 * 5. 작성일  : 2012. 8. 14. 오전 8:29:54
 * 6. 변경이력 : 
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    강현식 : 2012. 8. 14. :            : 신규 개발.
 */
package com.trouble.lrv.core.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author Choi Bin
 * @since 1.0 beta
 */
public class FileDownloadView extends AbstractView {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	public FileDownloadView(){
		setContentType("application/octet-stream;charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		File file = (File)model.get("downloadFile");
		String fileName = (String)model.get("fileName");
		
		FileInputStream fis = null;
		OutputStream out = null;
		// 파일이 존재할때만 처리
		if( file != null && file.isFile() ) {
			response.setContentType(getContentType());
			response.setContentLength((int)file.length());

			String userAgent = request.getHeader("User-Agent");

			boolean ie = userAgent.indexOf("MSIE") > -1;
			
			if(ie){
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "8859_1");
			}// end if;

			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");

			out = response.getOutputStream();
			
			try {
				fis = new FileInputStream(file);
				FileCopyUtils.copy(fis, out);
			} catch(Exception e) {
				if ((e instanceof java.net.SocketException) && e.getMessage().equals("Broken pipe")){
					// 무시
				}
				else {
					logger.error(e.getMessage(), e);
				}
			} finally {
				IOUtils.closeQuietly(fis);
				IOUtils.closeQuietly(out);
			}// try end;

		}else{
			
		}
		// 파일이 존재하지 않을때에는 에러 페이지 처리
		

	}// render() end;
}
