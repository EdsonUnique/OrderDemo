package edson.wechatfood.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryVO<T> {

    @JsonProperty("name")//给JSON数据重命名
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private T products;

}
