package com.shopping;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopping.entity.Product;
import com.shopping.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 商品数据批量生成测试类
 * <p>
 * 运行前置条件：
 * 1. MySQL数据库已创建 shopping_platform 数据库
 * 2. 已执行 schema.sql 创建 product 表
 * 3. application.yml 中数据库连接配置正确
 * 4. ProductMapper 和 Product 实体类已存在
 * 5. ProductService 和 ProductServiceImpl 已存在
 * 6. Redis 服务已启动（如项目配置了Redis）
 * <p>
 * 运行方式：
 * 直接在IDE中运行此测试类，或使用命令：mvn test -Dtest=ProductDataGeneratorTest
 */
@SpringBootTest
public class ProductDataGeneratorTest {

    @Autowired
    private ProductService productService;

    /**
     * 随机数生成器
     */
    private final Random random = new Random();

    /**
     * 批量插入大小，每100条执行一次insert
     */
    private static final int BATCH_SIZE = 100;

    /**
     * 总商品数量
     */
    private static final int TOTAL_COUNT = 1000;

    /**
     * 分类数量
     */
    private static final int CATEGORY_COUNT = 10;

    /**
     * 分类ID与名称映射
     */
    private static final int[] CATEGORY_IDS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final String[] CATEGORY_NAMES = {
            "数码电子", "手机通讯", "电脑整机&外设", "影音数码", "可穿戴设备",
            "摄影摄像", "智能家居", "智能大家电", "智能小家电", "手机配件"
    };

    /**
     * 商品名称模板（按分类）
     */
    private static final String[][] PRODUCT_NAMES_BY_CATEGORY = {
            // 1-数码电子
            {"无线蓝牙耳机", "智能手环", "移动电源", "USB-C数据线", "充电头快充",
             "便携蓝牙音箱", "无线鼠标", "机械键盘", "触控笔", "数码收纳包"},
            // 2-手机通讯
            {"智能手机旗舰版", "5G手机标准版", "游戏手机", "折叠屏手机", "商务手机",
             "千元机入门款", "大屏手机", "轻薄手机", "拍照手机", "三防手机"},
            // 3-电脑整机&外设
            {"轻薄笔记本电脑", "游戏本电脑", "台式机主机", "一体机电脑", "迷你主机",
             "机械键盘青轴", "RGB鼠标", "电竞显示器", "无线耳机", "笔记本支架"},
            // 4-影音数码
            {"降噪耳机", "HiFi播放器", "便携音箱", "蓝牙接收器", "数码相框",
             "录音笔", "MP3播放器", "耳机放大器", "音频解码器", "监听耳机"},
            // 5-可穿戴设备
            {"智能手表", "运动手环", "智能眼镜", "健康监测仪", "智能戒指",
             "健身追踪器", "智能手环防水", "GPS定位器", "心率监测手环", "睡眠监测仪"},
            // 6-摄影摄像
            {"数码相机", "单反相机", "微单相机", "运动相机", "摄像机",
             "三脚架", "相机包", "镜头滤镜", "闪光灯", "存储卡"},
            // 7-智能家居
            {"智能音箱", "智能门锁", "智能灯泡", "智能开关", "智能窗帘",
             "智能摄像头", "智能扫地机器人", "智能空调伴侣", "智能插座", "智能门铃"},
            // 8-智能大家电
            {"智能电视", "智能冰箱", "智能洗衣机", "智能空调", "智能热水器",
             "智能洗碗机", "智能烤箱", "智能消毒柜", "智能烘干机", "智能净水器"},
            // 9-智能小家电
            {"咖啡机", "空气炸锅", "破壁机", "料理机", "电饭煲",
             "电压力锅", "电烤箱", "微波炉", "加湿器", "空气净化器"},
            // 10-手机配件
            {"手机壳", "钢化膜", "手机支架", "无线充电器", "手机数据线",
             "手机耳机", "手机充电宝", "手机镜头", "手机贴膜工具", "手机清洁套装"}
    };

    /**
     * 商品副标题模板
     */
    private static final String[] SUBTITLES = {
            "正品保障", "官方授权", "全国联保", "顺丰包邮", "限时特惠",
            "新品上市", "热销爆款", "品质之选", "值得信赖", "超值之选"
    };

    /**
     * 公开无版权图片URL数组（随机抽取）
     */
    private static final String[] IMAGE_URLS = {
            "https://picsum.photos/400/400?random=1",
            "https://picsum.photos/400/400?random=2",
            "https://picsum.photos/400/400?random=3",
            "https://picsum.photos/400/400?random=4",
            "https://picsum.photos/400/400?random=5",
            "https://picsum.photos/400/400?random=6",
            "https://picsum.photos/400/400?random=7",
            "https://picsum.photos/400/400?random=8",
            "https://picsum.photos/400/400?random=9",
            "https://picsum.photos/400/400?random=10",
            "https://picsum.photos/400/400?random=11",
            "https://picsum.photos/400/400?random=12",
            "https://picsum.photos/400/400?random=13",
            "https://picsum.photos/400/400?random=14",
            "https://picsum.photos/400/400?random=15",
            "https://picsum.photos/400/400?random=16",
            "https://picsum.photos/400/400?random=17",
            "https://picsum.photos/400/400?random=18",
            "https://picsum.photos/400/400?random=19",
            "https://picsum.photos/400/400?random=20"
    };

    /**
     * 商品描述模板
     */
    private static final String[] PRODUCT_DESCRIPTIONS = {
            "高品质数码产品，品质保证，售后无忧。",
            "精选优质材料，精湛工艺，值得信赖。",
            "性能卓越，体验非凡，让生活更智能。",
            "时尚设计，轻薄便携，随时随地享受科技带来的便利。",
            "采用先进技术，功能强大，满足您的各种需求。",
            "性价比超高，物超所值，是您的明智之选。",
            "安全可靠，经久耐用，陪伴您每一天。",
            "简约时尚，美观大方，为您的生活增添色彩。",
            "多功能设计，一机多用，节省空间更省心。",
            "快速响应，流畅体验，让您的操作更加便捷。"
    };

    /**
     * 生成随机价格（9.9 ~ 9999.99）
     */
    private BigDecimal generatePrice() {
        double min = 9.9;
        double max = 9999.99;
        double price = min + (max - min) * random.nextDouble();
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 生成随机库存（0 ~ 500）
     */
    private int generateStock() {
        return random.nextInt(501);
    }

    /**
     * 生成随机状态（0下架，1上架）
     */
    private int generateStatus() {
        return random.nextInt(2);
    }

    /**
     * 随机获取图片URL
     */
    private String getRandomImageUrl() {
        return IMAGE_URLS[random.nextInt(IMAGE_URLS.length)];
    }

    /**
     * 生成子图片URL（逗号拼接2~4张）
     */
    private String generateSubImages() {
        int count = 2 + random.nextInt(3); // 2~4张
        List<String> images = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            images.add(getRandomImageUrl());
        }
        return String.join(",", images);
    }

    /**
     * 根据分类ID生成商品名称（确保不重复）
     */
    private String generateProductName(int categoryIndex, int productIndex) {
        String baseName = PRODUCT_NAMES_BY_CATEGORY[categoryIndex][productIndex % PRODUCT_NAMES_BY_CATEGORY[categoryIndex].length];
        String[] suffixes = {"标准版", "升级版", "Pro版", "Max版", "Mini版", "青春版", "尊享版", "旗舰版", "豪华版", "精英版"};
        return baseName + suffixes[productIndex / PRODUCT_NAMES_BY_CATEGORY[categoryIndex].length];
    }

    /**
     * 生成商品描述
     */
    private String generateDescription() {
        return PRODUCT_DESCRIPTIONS[random.nextInt(PRODUCT_DESCRIPTIONS.length)];
    }

    /**
     * 批量生成并插入1000条商品数据
     */
    @Test
    public void batchGenerateProducts() {
        System.out.println("========================================");
        System.out.println("开始批量生成商品数据...");
        System.out.println("========================================");

        long startTime = System.currentTimeMillis();
        int totalInserted = 0;

        // 每个分类生成约100条商品
        for (int categoryIndex = 0; categoryIndex < CATEGORY_COUNT; categoryIndex++) {
            int categoryId = CATEGORY_IDS[categoryIndex];
            String categoryName = CATEGORY_NAMES[categoryIndex];
            int productsPerCategory = TOTAL_COUNT / CATEGORY_COUNT;

            System.out.println("正在生成分类 [" + categoryName + "] 的商品数据...");

            List<Product> batchList = new ArrayList<>();

            for (int i = 0; i < productsPerCategory; i++) {
                Product product = new Product();
                product.setCategoryId(Long.valueOf(categoryId));
                product.setName(generateProductName(categoryIndex, i));
                product.setSubtitle(SUBTITLES[random.nextInt(SUBTITLES.length)]);
                product.setMainImage(getRandomImageUrl());
                product.setDetail(generateDescription());
                product.setPrice(generatePrice());
                product.setStock(generateStock());
                product.setSales(random.nextInt(1000));
                product.setVersion(0);
                product.setStatus(generateStatus());
                product.setIsDeleted(0);
                product.setCreateTime(LocalDateTime.now());
                product.setUpdateTime(LocalDateTime.now());

                batchList.add(product);

                // 每100条执行一次批量插入
                if (batchList.size() >= BATCH_SIZE) {
                    productService.saveBatch(batchList, BATCH_SIZE);
                    totalInserted += batchList.size();
                    System.out.println("  已插入 " + totalInserted + " 条商品");
                    batchList.clear();
                }
            }

            // 处理剩余不足100条的数据
            if (!batchList.isEmpty()) {
                productService.saveBatch(batchList, batchList.size());
                totalInserted += batchList.size();
                System.out.println("  已插入 " + totalInserted + " 条商品");
                batchList.clear();
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 验证插入结果
        long count = productService.count();

        System.out.println("========================================");
        System.out.println("批量生成完成！");
        System.out.println("总插入条数: " + totalInserted);
        System.out.println("数据库总条数: " + count);
        System.out.println("耗时: " + duration + " 毫秒");
        System.out.println("========================================");
    }

    /**
     * 查询验证数据是否正确插入
     */
    @Test
    public void verifyData() {
        System.out.println("========================================");
        System.out.println("验证商品数据...");
        System.out.println("========================================");

        // 查询总条数
        long total = productService.count();
        System.out.println("总商品数: " + total);

        // 按分类统计
        for (int i = 0; i < CATEGORY_COUNT; i++) {
            long count = productService.count(
                    new LambdaQueryWrapper<Product>()
                            .eq(Product::getCategoryId, CATEGORY_IDS[i])
            );
            System.out.println("分类 [" + CATEGORY_NAMES[i] + "]: " + count + " 条");
        }

        // 查询上架/下架数量
        long activeCount = productService.count(
                new LambdaQueryWrapper<Product>().eq(Product::getStatus, 1)
        );
        long inactiveCount = productService.count(
                new LambdaQueryWrapper<Product>().eq(Product::getStatus, 0)
        );
        System.out.println("上架商品: " + activeCount + " 条");
        System.out.println("下架商品: " + inactiveCount + " 条");

        System.out.println("========================================");
    }
}
