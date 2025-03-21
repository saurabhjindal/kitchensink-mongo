package org.jboss.as.quickstarts.kitchensink.service.impl;


import org.jboss.as.quickstarts.kitchensink.entity.Counter;
import org.jboss.as.quickstarts.kitchensink.service.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class SequenceGeneratorImpl implements SequenceGenerator {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    @Transactional
    public Long generateSequence(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        Update update = new Update().inc("sequenceValue", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        Counter counter = mongoOperations.findAndModify(query, update, options, Counter.class);
        return counter != null ? counter.getSequenceValue() : 1;
    }

}

