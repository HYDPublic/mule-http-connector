/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.http.functional.requester;


import static org.mule.test.http.AllureConstants.HttpFeature.HTTP_EXTENSION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.api.exception.MessagingException;

import java.security.GeneralSecurityException;

import org.junit.Test;
import io.qameta.allure.Feature;

@Feature(HTTP_EXTENSION)
public class HttpRequestValidateCertificateTestCase extends AbstractHttpRequestTestCase {

  @Override
  protected String getConfigFile() {
    return "http-request-validate-certificate-config.xml";
  }

  @Override
  protected boolean enableHttps() {
    return true;
  }

  @Test
  public void rejectsMissingCertificate() throws Exception {
    MessagingException e = flowRunner("missingCertFlow").withPayload(TEST_MESSAGE).runExpectingException();
    assertThat(e, is(instanceOf(MessagingException.class)));
    assertThat(e.getRootCause(), is(instanceOf(GeneralSecurityException.class)));
  }

  @Test
  public void acceptsValidCertificate() throws Exception {
    CoreEvent result = flowRunner("validCertFlow").withPayload(TEST_MESSAGE).run();
    assertThat(result.getMessage().getPayload().getValue(), equalTo(DEFAULT_RESPONSE));
  }
}
