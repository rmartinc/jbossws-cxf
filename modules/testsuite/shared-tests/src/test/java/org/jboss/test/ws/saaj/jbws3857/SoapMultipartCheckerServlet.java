/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.ws.saaj.jbws3857;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBodyElement;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPMessage;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 */
public class SoapMultipartCheckerServlet extends HttpServlet {

   @Override
   protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
      final String requestedContentType = req.getHeader("Content-Type");
      System.out.println("Requested content type : " + requestedContentType);

      try {
         replyRequestedContentTypeInResponse(requestedContentType, resp);
      } catch (final Exception e) {
         throw new ServletException(e.getMessage(), e);
      }
   }

   private void replyRequestedContentTypeInResponse(final String requestedContentType, final HttpServletResponse resp) throws Exception {
      final MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
      final SOAPMessage msg = msgFactory.createMessage();
      final SOAPBodyElement bodyElement = msg.getSOAPBody().addBodyElement(
         new QName("urn:not-important:1.0", "requestedContentType"));

      bodyElement.addTextNode(requestedContentType);

      resp.setStatus(HttpServletResponse.SC_OK);
      resp.setHeader("Content-Type", "application/soap+xml; charset=utf-8");
      final OutputStream os = resp.getOutputStream();
      msg.writeTo(os);
      os.close();
   }
}
