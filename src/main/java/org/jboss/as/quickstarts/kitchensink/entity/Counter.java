package org.jboss.as.quickstarts.kitchensink.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "counter")
@AllArgsConstructor
@NoArgsConstructor
public class Counter {

    @Id
    private String id;

    private Long sequenceValue;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public long getSequenceValue() { return sequenceValue; }
    public void setSequenceValue(long sequenceValue) { this.sequenceValue = sequenceValue; }
}

