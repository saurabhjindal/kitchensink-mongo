package org.jboss.as.quickstarts.kitchensink.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.jboss.as.quickstarts.kitchensink.entity.Counter;
import org.jboss.as.quickstarts.kitchensink.service.impl.SequenceGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

class SequenceGeneratorImplTest {

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private SequenceGeneratorImpl sequenceGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateSequence_WhenCounterExists_ShouldIncrementAndReturnNewValue() {
        String sequenceName = "test_sequence";
        Counter counter = new Counter(sequenceName, 10L);

        when(mongoOperations.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), eq(Counter.class)))
                .thenReturn(counter);

        Long result = sequenceGenerator.generateSequence(sequenceName);

        assertEquals(10L, result);
        verify(mongoOperations, times(1)).findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), eq(Counter.class));
    }

    @Test
    void generateSequence_WhenCounterDoesNotExist_ShouldReturnOne() {
        String sequenceName = "test_sequence";

        when(mongoOperations.findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), eq(Counter.class)))
                .thenReturn(null);

        Long result = sequenceGenerator.generateSequence(sequenceName);

        assertEquals(1L, result);
        verify(mongoOperations, times(1)).findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), eq(Counter.class));
    }
}