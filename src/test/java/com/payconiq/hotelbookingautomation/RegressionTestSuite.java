package com.payconiq.hotelbookingautomation;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;


@SelectPackages("com.payconiq.hotelbookingautomation.tests")
@IncludeTags({"Regression"})
@Suite
@SuiteDisplayName("Regression Suite for Hotel Booking Platform")
public class RegressionTestSuite {

}
