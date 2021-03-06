/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.http.functional.requester;

import static org.mule.test.http.AllureConstants.HttpFeature.HTTP_EXTENSION;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.mule.runtime.core.api.exception.MessagingException;
import org.mule.tck.junit4.AbstractMuleContextTestCase;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import io.qameta.allure.Feature;

@Feature(HTTP_EXTENSION)
public class HttpRequestUriParamsTestCase extends AbstractHttpRequestTestCase {

  @Override
  protected String getConfigFile() {
    return "http-request-uri-params-config.xml";
  }

  @Test
  public void sendsUriParamsFromList() throws Exception {
    flowRunner("uriParamList").withPayload(AbstractMuleContextTestCase.TEST_MESSAGE).withVariable("paramName", "testParam2")
        .withVariable("paramValue", "testValue2").run();
    assertThat(uri, equalTo("/testPath/testValue1/testValue2"));
  }

  @Test
  public void sendsUriParamsFromMap() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("testParam1", "testValue1");
    params.put("testParam2", "testValue2");
    flowRunner("uriParamMap").withPayload(AbstractMuleContextTestCase.TEST_MESSAGE).withVariable("params", params).run();

    assertThat(uri, equalTo("/testPath/testValue1/testValue2"));
  }

  @Test
  public void overridesUriParams() throws Exception {
    Map<String, String> params = new HashMap<>();
    params.put("testParam1", "testValueNew");
    params.put("testParam2", "testValue2");
    flowRunner("uriParamOverride").withPayload(AbstractMuleContextTestCase.TEST_MESSAGE).withVariable("params", params).run();

    assertThat(uri, equalTo("/testPath/testValueNew/testValue2"));
  }

  @Test
  public void sendsUriParamsIfNull() throws Exception {
    MessagingException expectedException = flowRunner("uriParamNull").runExpectingException();
    assertThat(expectedException.getCause(), instanceOf(NullPointerException.class));
    assertThat(expectedException.getMessage(), containsString("Expression {testParam2} evaluated to null."));
  }

}
