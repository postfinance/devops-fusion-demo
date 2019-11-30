// Copyright 2020 by PostFinance Ltd - all rights reserved
package ch.postfinance.devops.fusion.demo.thirt.party.simulator.xpath;

public class XPathMatcher {

    private XPathMatcher() {
        // Static access only
    }

    public static String rootNodeMatchingQName(String rootNode, String qName) {
        return "string:local-name(/*) = '" + rootNode + "' and namespace-uri(/*) = '" + qName + "'";
    }

    public static String nodeMatchingLocalNameAndQName(String nodeName, String qName) {
        return "//*[ local-name() = '" + nodeName + "' and namespace-uri() = '" + qName + "' ]";
    }
}
