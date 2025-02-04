/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.ws.jaxws.jbws3552;

import java.net.URL;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Filter;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.wsf.test.JBossWSTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * [JBWS-3552] @XmlJavaTypeAdapter ignored on exception classes.
 *
 * Suffixes abbreviations used in this test case in class names are:
 * <ul>
 *   <li>CA - class level access</li>
 *   <li>FA - field level access</li>
 *   <li>MA - method level access</li>
 *   <li>GA - package level access</li>
 * </ul>
 * @author <a href="ropalka@redhat.com">Richard Opalka</a>
 */
@RunWith(Arquillian.class)
public class JBWS3552TestCase extends JBossWSTest {

   @ArquillianResource
   private URL baseURL;

   @Deployment(testable = false)
   public static WebArchive createDeployments() {
      WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxws-jbws3552.war");
         archive
            .addManifest()
            .addPackages(false, new Filter<ArchivePath>() {
               @Override
               public boolean include(ArchivePath path)
               {
                  return !path.get().contains("TestCase");
               }}, "org.jboss.test.ws.jaxws.jbws3552");
      return archive;
   }

   private EndpointIface getProxy() throws Exception {
      final URL wsdlURL = new URL(baseURL + "/EndpointImpl?wsdl");
      final QName serviceName = new QName("http://jbws3552.jaxws.ws.test.jboss.org/", "EndpointImplService");
      final Service service = Service.create(wsdlURL, serviceName);
      return service.getPort(EndpointIface.class);
   }

   @Test
   @RunAsClient
    public void testEchoCA() throws Exception {
        EndpointIface endpoint = getProxy();
        AdaptedObjectCA aoCA = new AdaptedObjectCA(444, "object message", "object description", new ComplexObjectCA("a", "b"));
        assertEquals("444,object message,object description,a b", endpoint.echoCA(aoCA).toString());
    }

   @Test
   @RunAsClient
    public void testEchoAbstractCA() throws Exception {
        EndpointIface endpoint = getProxy();
        AbstractObjectCA aoCA = new AdaptedObjectCA(444, "object message", "object description", new ComplexObjectCA("a", "b"));
        assertEquals("444,object message,object description,a b", endpoint.echoAbstractCA(aoCA).toString());
    }

   @Test
   @RunAsClient
    public void testEchoFA() throws Exception {
        EndpointIface endpoint = getProxy();
        AdaptedObjectFA aoFA = new AdaptedObjectFA(444, "object message", "object description", new ComplexObjectFA("a", "b"));
        assertEquals("444,object message,object description,a b", endpoint.echoFA(aoFA).toString());
    }

   @Test
   @RunAsClient
    public void testEchoAbstractFA() throws Exception {
        EndpointIface endpoint = getProxy();
        AbstractObjectFA aoFA = new AdaptedObjectFA(444, "object message", "object description", new ComplexObjectFA("a", "b"));
        assertEquals("444,object message,object description,a b", endpoint.echoAbstractFA(aoFA).toString());
    }

   @Test
   @RunAsClient
    public void testEchoGA() throws Exception {
        EndpointIface endpoint = getProxy();
        AdaptedObjectGA aoGA = new AdaptedObjectGA(444, "object message", "object description", new ComplexObjectGA("a", "b"));
        assertEquals("444,object message,object description,a b", endpoint.echoGA(aoGA).toString());
    }

   @Test
   @RunAsClient
    public void testEchoAbstractGA() throws Exception {
        EndpointIface endpoint = getProxy();
        AbstractObjectGA aoGA = new AdaptedObjectGA(444, "object message", "object description", new ComplexObjectGA("a", "b"));
        assertEquals("444,object message,object description,a b", endpoint.echoAbstractGA(aoGA).toString());
    }

   @Test
   @RunAsClient
    public void testEchoMA() throws Exception {
        EndpointIface endpoint = getProxy();
        AdaptedObjectMA aoMA = new AdaptedObjectMA(444, "object message", "object description", new ComplexObjectMA("a", "b"));
        assertEquals("444,object message,object description,a b", endpoint.echoMA(aoMA).toString());
    }

   @Test
   @RunAsClient
    public void testEchoAbstractMA() throws Exception {
        EndpointIface endpoint = getProxy();
        AbstractObjectMA aoMA = new AdaptedObjectMA(444, "object message", "object description", new ComplexObjectMA("a", "b"));
        assertEquals("444,object message,object description,a b", endpoint.echoAbstractMA(aoMA).toString());
    }

   @Test
   @RunAsClient
   @Ignore(value="FIXME: [CXF-4600] Exception inheritance not working over SOAP protocol")
    public void testExceptionCA() throws Exception {
        EndpointIface endpoint = getProxy();
        try {
            endpoint.throwExceptionCA();
            fail("Expected exception not thrown");
        } catch (AdaptedExceptionCA e) {
            assertEquals("666,exception message,exception description,c d", e.toString());
        }
    }

   @Test
   @RunAsClient
    public void testExtendedExceptionCA() throws Exception {
        EndpointIface endpoint = getProxy();
        try {
            endpoint.throwExtendedExceptionCA();
            fail("Expected exception not thrown");
        } catch (ExtendedAdaptedExceptionCA e) {
            assertEquals("666,exception message,exception description,c d", e.toString());
        }
    }

   @Test
   @RunAsClient
   @Ignore("FIXME: [CXF-4600] Exception inheritance not working over SOAP protocol") 
    public void testExceptionFA() throws Exception {
        EndpointIface endpoint = getProxy();
        try {
            endpoint.throwExceptionFA();
            fail("Expected exception not thrown");
        } catch (AdaptedExceptionFA e) {
            assertEquals("666,exception message,exception description,c d", e.toString());
        }
    }

   @Test
   @RunAsClient
    public void testExtendedExceptionFA() throws Exception {
        EndpointIface endpoint = getProxy();
        try {
            endpoint.throwExtendedExceptionFA();
            fail("Expected exception not thrown");
        } catch (ExtendedAdaptedExceptionFA e) {
            assertEquals("666,exception message,exception description,c d", e.toString());
        }
    }

   @Test
   @RunAsClient
   @Ignore("FIXME: [CXF-4600] Exception inheritance not working over SOAP protocol") 
    public void testExceptionGA() throws Exception {
        EndpointIface endpoint = getProxy();        
        try {
            endpoint.throwExceptionGA();
            fail("Expected exception not thrown");
        } catch (AdaptedExceptionGA e) {
            assertEquals("666,exception message,exception description,c d", e.toString());
        }
    }

   @Test
   @RunAsClient
    public void testExtendedExceptionGA() throws Exception {
        EndpointIface endpoint = getProxy();
        try {
            endpoint.throwExtendedExceptionGA();
            fail("Expected exception not thrown");
        } catch (ExtendedAdaptedExceptionGA e) {
            assertEquals("666,exception message,exception description,c d", e.toString());
        }
    }

   @Test
   @RunAsClient
   @Ignore("FIXME: [CXF-4600] Exception inheritance not working over SOAP protocol")
    public void testExceptionMA() throws Exception {
        EndpointIface endpoint = getProxy();        
        try {
            endpoint.throwExceptionMA();
            fail("Expected exception not thrown");
        } catch (AdaptedExceptionMA e) {
            assertEquals("666,exception message,exception description,c d", e.toString());
        }
    }

   @Test
   @RunAsClient
    public void testExtendedExceptionMA() throws Exception {
        EndpointIface endpoint = getProxy();
        try {
            endpoint.throwExtendedExceptionMA();
            fail("Expected exception not thrown");
        } catch (ExtendedAdaptedExceptionMA e) {
            assertEquals("666,exception message,exception description,c d", e.toString());
        }
    }
}
