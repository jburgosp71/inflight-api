package com.airshop.inflightapi.adapter.persistence.mapper;

import com.airshop.inflightapi.adapter.persistence.entity.PaymentDetailsEmbeddable;
import com.airshop.inflightapi.domain.model.PaymentDetails;

public class PaymentDetailsMapper {

    public static PaymentDetails toDomain(PaymentDetailsEmbeddable embeddable) {
        if (embeddable == null) {
            return PaymentDetails.empty();
        }

        return new PaymentDetails(
                embeddable.getTotalPrice(),
                embeddable.getCardToken(),
                embeddable.getStatus(),
                embeddable.getPaymentDate(),
                embeddable.getGateway()
        );
    }

    public static PaymentDetailsEmbeddable toEntity(PaymentDetails details) {
        if (details == null) {
            return new PaymentDetailsEmbeddable(); // Devuelve un objeto vacío
        }

        PaymentDetailsEmbeddable entity = new PaymentDetailsEmbeddable();
        entity.setTotalPrice(details.getTotalPrice());
        entity.setCardToken(details.getCardToken());
        entity.setStatus(details.getStatus());
        entity.setPaymentDate(details.getPaymentDate());
        entity.setGateway(details.getGateway());
        return entity;
    }
}
