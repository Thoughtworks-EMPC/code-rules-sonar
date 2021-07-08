


import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "payment-service",
  url = "${feign.client.config.payment-service.url:payment-service}",
  primary = false)
public interface PaymentServiceClient {


}
