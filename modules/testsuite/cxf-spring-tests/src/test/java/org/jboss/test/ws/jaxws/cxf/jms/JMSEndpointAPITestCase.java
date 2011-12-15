/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.ws.jaxws.cxf.jms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import junit.framework.Test;

import org.jboss.wsf.test.JBossWSCXFTestSetup;
import org.jboss.wsf.test.JBossWSTest;

/**
 * Test case for publishing a JMS (SOAP-over-JMS 1.0) endpoint through API 
 *
 * @author alessio.soldano@jboss.com
 * @since 29-Apr-2011
 */
public final class JMSEndpointAPITestCase extends JBossWSTest
{
   public static Test suite()
   {
      return new JBossWSCXFTestSetup(JMSEndpointAPITestCase.class, "jaxws-cxf-jms-api-as7-client.jar,jaxws-cxf-jms-api-as7.war");
   }
   
   public void testServerSide() throws Exception
   {
      URL url = new URL("http://" + getServerHost() + ":8080/jaxws-cxf-jms-api-as7");
      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
      assertEquals("true", br.readLine());
   }
   
   public void testClientSide() throws Exception
   {
      if (isTargetJBoss7()) {
         System.out.println("FIXME: can't lookup ConnectionFactory, remote JNDI binding not available yet on AS7");
         return;
      }
      URL wsdlUrl = Thread.currentThread().getContextClassLoader().getResource("META-INF/wsdl/HelloWorldService.wsdl");
      Object implementor = new HelloWorldImpl();
      Endpoint ep = Endpoint.publish("jms:queue:testQueue", implementor);
      try
      {
         QName serviceName = new QName("http://org.jboss.ws/jaxws/cxf/jms", "HelloWorldService");
         
         Service service = Service.create(wsdlUrl, serviceName);
         HelloWorld proxy = (HelloWorld) service.getPort(new QName("http://org.jboss.ws/jaxws/cxf/jms", "HelloWorldImplPort"), HelloWorld.class);
         assertEquals("Hi", proxy.echo("Hi"));
      }
      finally
      {
         ep.stop();
      }
   }
}
