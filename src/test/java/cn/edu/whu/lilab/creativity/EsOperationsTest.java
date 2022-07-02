package cn.edu.whu.lilab.creativity;

import cn.edu.whu.lilab.creativity.domain.Product;
import io.swagger.annotations.Authorization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@SpringBootTest
public class EsOperationsTest {


    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public EsOperationsTest(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * save 索引一条文档 更新一条 文档
     *  save 方法当文档id 不存在时添加文档,当文档 id 存在时候更新文档
     */
    @Test
    public  void testIndex(){
        Product product = new Product();
        product.setId(2);
        product.setTitle("日本豆");
        product.setPrice(5.5);
        product.setDescription("日本豆真好吃,曾经非常爱吃!");
        elasticsearchOperations.save(product);
    }
}
