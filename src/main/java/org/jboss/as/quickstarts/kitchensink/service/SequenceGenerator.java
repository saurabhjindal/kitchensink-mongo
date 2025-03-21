package org.jboss.as.quickstarts.kitchensink.service;

public interface SequenceGenerator {

    Long generateSequence(String seqName);
}
