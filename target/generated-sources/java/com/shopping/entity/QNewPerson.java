package com.shopping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNewPerson is a Querydsl query type for NewPerson
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNewPerson extends EntityPathBase<NewPerson> {

    private static final long serialVersionUID = 956572227L;

    public static final QNewPerson newPerson = new QNewPerson("newPerson");

    public final StringPath address = createString("address");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> salary = createNumber("salary", Integer.class);

    public QNewPerson(String variable) {
        super(NewPerson.class, forVariable(variable));
    }

    public QNewPerson(Path<? extends NewPerson> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNewPerson(PathMetadata metadata) {
        super(NewPerson.class, metadata);
    }

}

