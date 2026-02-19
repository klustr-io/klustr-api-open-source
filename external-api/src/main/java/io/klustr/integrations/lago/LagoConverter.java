package io.klustr.integrations.lago;

import com.lago.openapi.model.*;
import io.klustr.schemas.console.Address;
import io.klustr.schemas.console.accounts.Account;
import io.klustr.schemas.console.billing.BillingCustomer;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class LagoConverter {

    public static final String DEFAULT_CURRENCY = "USD";

    public static LagoCustomerCreateInput input(BillingCustomer customer) {
        if (customer.getExternalSource() != null) {
            if (!customer.getExternalSource().equalsIgnoreCase("lago")) {
                throw new RuntimeException("Customer is not loaded into lago, it is from a different source.");
            }
        }
        LagoCustomerCreateInput model = new com.lago.openapi.model.LagoCustomerCreateInput();
        LagoCustomerCreateInputCustomer v = new LagoCustomerCreateInputCustomer();
        if (customer.getAddress() != null) {
            v.addressLine1(customer.getAddress().getAddressLine1())
                    .addressLine2(customer.getAddress().getAddressLine2())
                    .externalId(customer.getId())
                    .city(customer.getAddress().getCityWard())
                    .state(customer.getAddress().getStatePrefecture())
                    .zipcode(customer.getAddress().getPostalCode())
                    .country(customer.getAddress().getCountryCode() != null ? LagoCountry.valueOf(customer.getAddress().getCountryCode().value()) : null);
        }
        String value = customer.getCustomerType() != null ? customer.getCustomerType().value() : "company";
        LagoCustomerCreateInputCustomer.CustomerTypeEnum customerType = LagoCustomerCreateInputCustomer.CustomerTypeEnum.fromValue(value);
        v.customerType(customerType)
                .externalId(customer.getId())
                .currency(LagoCurrency.valueOf(customer.getCurrency()))
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .legalName(customer.getLegalName())
                .url(customer.getUrl())
                .legalNumber(customer.getLegalNumber())
                .url(customer.getUrl())
                .logoUrl(customer.getLogoUrl());

        model.setCustomer(v);
        return model;
    }

    public static com.lago.openapi.model.LagoCustomer from(BillingCustomer customer) {
        if (customer.getExternalSource() != null) {
            if (!customer.getExternalSource().equalsIgnoreCase("lago")) {
                throw new RuntimeException("Customer is not loaded into lago, it is from a different source.");
            }
        }
        com.lago.openapi.model.LagoCustomer model = new com.lago.openapi.model.LagoCustomer();
        LagoCustomerObjectExtended v = new LagoCustomerObjectExtended();
        if (customer.getAddress() != null) {
            v.addressLine1(customer.getAddress().getAddressLine1())
                    .addressLine2(customer.getAddress().getAddressLine2())
                    .externalId(customer.getId())
                    .city(customer.getAddress().getCityWard())
                    .state(customer.getAddress().getStatePrefecture())
                    .zipcode(customer.getAddress().getPostalCode())
                    .country(customer.getAddress().getCountryCode() != null ? LagoCountry.valueOf(customer.getAddress().getCountryCode().value()) : null);
        }
        LagoCustomerObjectExtended.CustomerTypeEnum customerType = LagoCustomerObjectExtended.CustomerTypeEnum.fromValue(customer.getCustomerType().value());
        v.customerType(customerType)
            .lagoId(UUID.fromString(customer.getExternalId()))
            .currency(LagoCurrency.valueOf(customer.getCurrency()))
            .email(customer.getEmail())
            .phone(customer.getPhone())
            .firstname(customer.getFirstname())
            .lastname(customer.getLastname())
            .legalName(customer.getLegalName())
            .url(customer.getUrl())
            .legalNumber(customer.getLegalNumber())
            .url(customer.getUrl())
            .logoUrl(customer.getLogoUrl());

        model.setCustomer(v);
        return model;
    }

    public static BillingCustomer fromCustomer(com.lago.openapi.model.LagoCustomer customer) {

        LagoCustomerObjectExtended c = customer.getCustomer();
        String country_code = c.getCountry() != null ? c.getCountry().getValue() : null;
        BillingCustomer result = new BillingCustomer()
                .withId(c.getLagoId().toString())
                .withFirstname(c.getFirstname())
                .withLastname(c.getLastname())
                .withLegalName(c.getLegalName())
                .withLegalNumber(c.getLegalNumber())
                .withEmail(customer.getCustomer().getEmail())
                .withLogoUrl(c.getLogoUrl())
                .withName(c.getName())
                // .withCustomerType(c.getCustomerType() != null ? BillingCustomer.BillingCustomerType.COMPANY : BillingCustomer.BillingCustomerType.fromValue( c.getCustomerType().getValue()))
                .withCurrency(c.getCurrency() != null ? c.getCurrency().getValue() : DEFAULT_CURRENCY)
                .withPhone(c.getPhone())
                .withExternalId(c.getExternalId())
                .withExternalSource("lago")
                .withExternalSalesForceId(c.getExternalSalesforceId())
                .withTimezone(c.getApplicableTimezone().getValue())
                .withUrl(c.getUrl())
                .withAddress(new Address()
                        .withAddressLine1(c.getAddressLine1())
                        .withAddressLine2(c.getAddressLine2())
                        .withCityWard(c.getCity())
                        .withCountryCode(StringUtils.isNotBlank(country_code) ? Address.CountryCode.valueOf(country_code) : null)
                        .withPostalCode(c.getZipcode())
                        .withStatePrefecture(c.getState())
                );

        String json = U.toJson(c);
        return result;
    }

    public static BillingCustomer toCustomer(Org obj) {
        BillingCustomer c = new BillingCustomer()
                .withId(obj.getId())
                .withCurrency(DEFAULT_CURRENCY)
                .withName(obj.getName())
                .withLegalName(obj.getName())
                .withLogoUrl(obj.getLogoUrl())
                .withExternalId(obj.getId())
                .withExternalSalesForceId(obj.getExternalSalesForceId());

        if (obj.getAddress() != null) {
            c.withAddress(obj.getAddress());
        }

        if (obj.getContact() != null) {
            c.withUrl(c.getUrl())
                    .withPhone(c.getPhone())
                    .withEmail(c.getEmail());
        }
        return c;
    }
}
