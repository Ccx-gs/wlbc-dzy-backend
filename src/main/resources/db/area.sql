-- 省市区三级数据表
DROP TABLE IF EXISTS area;

CREATE TABLE area (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    code VARCHAR(12) NOT NULL COMMENT '行政区划代码',
    name VARCHAR(100) NOT NULL COMMENT '名称',
    level INT NOT NULL COMMENT '级别：1=省/直辖市，2=市，3=区/县',
    parent_id BIGINT DEFAULT NULL COMMENT '上级ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_code (code),
    INDEX idx_parent_id (parent_id),
    INDEX idx_level (level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='全国省市区数据表';

-- ==================== 省级数据 ====================
INSERT INTO area (code, name, level, parent_id) VALUES
('110000', '北京市', 1, NULL),
('120000', '天津市', 1, NULL),
('130000', '河北省', 1, NULL),
('140000', '山西省', 1, NULL),
('150000', '内蒙古自治区', 1, NULL),
('210000', '辽宁省', 1, NULL),
('220000', '吉林省', 1, NULL),
('230000', '黑龙江省', 1, NULL),
('310000', '上海市', 1, NULL),
('320000', '江苏省', 1, NULL),
('330000', '浙江省', 1, NULL),
('340000', '安徽省', 1, NULL),
('350000', '福建省', 1, NULL),
('360000', '江西省', 1, NULL),
('370000', '山东省', 1, NULL),
('410000', '河南省', 1, NULL),
('420000', '湖北省', 1, NULL),
('430000', '湖南省', 1, NULL),
('440000', '广东省', 1, NULL),
('450000', '广西壮族自治区', 1, NULL),
('460000', '海南省', 1, NULL),
('500000', '重庆市', 1, NULL),
('510000', '四川省', 1, NULL),
('520000', '贵州省', 1, NULL),
('530000', '云南省', 1, NULL),
('540000', '西藏自治区', 1, NULL),
('610000', '陕西省', 1, NULL),
('620000', '甘肃省', 1, NULL),
('630000', '青海省', 1, NULL),
('640000', '宁夏回族自治区', 1, NULL),
('650000', '新疆维吾尔自治区', 1, NULL),
('710000', '台湾省', 1, NULL),
('810000', '香港特别行政区', 1, NULL),
('820000', '澳门特别行政区', 1, NULL);

-- ==================== 市级数据 ====================
-- 北京市下属区
INSERT INTO area (code, name, level, parent_id) VALUES
('110100', '北京市', 2, (SELECT id FROM area WHERE code='110000')),
('110101', '东城区', 3, (SELECT id FROM area WHERE code='110100')),
('110102', '西城区', 3, (SELECT id FROM area WHERE code='110100')),
('110105', '朝阳区', 3, (SELECT id FROM area WHERE code='110100')),
('110106', '丰台区', 3, (SELECT id FROM area WHERE code='110100')),
('110107', '石景山区', 3, (SELECT id FROM area WHERE code='110100')),
('110108', '海淀区', 3, (SELECT id FROM area WHERE code='110100')),
('110109', '门头沟区', 3, (SELECT id FROM area WHERE code='110100')),
('110111', '房山区', 3, (SELECT id FROM area WHERE code='110100')),
('110112', '通州区', 3, (SELECT id FROM area WHERE code='110100')),
('110113', '顺义区', 3, (SELECT id FROM area WHERE code='110100')),
('110114', '昌平区', 3, (SELECT id FROM area WHERE code='110100')),
('110115', '大兴区', 3, (SELECT id FROM area WHERE code='110100')),
('110116', '怀柔区', 3, (SELECT id FROM area WHERE code='110100')),
('110117', '平谷区', 3, (SELECT id FROM area WHERE code='110100')),
('110118', '密云区', 3, (SELECT id FROM area WHERE code='110100')),
('110119', '延庆区', 3, (SELECT id FROM area WHERE code='110100'));

-- 上海市下属区
INSERT INTO area (code, name, level, parent_id) VALUES
('310100', '上海市', 2, (SELECT id FROM area WHERE code='310000')),
('310101', '黄浦区', 3, (SELECT id FROM area WHERE code='310100')),
('310104', '徐汇区', 3, (SELECT id FROM area WHERE code='310100')),
('310105', '长宁区', 3, (SELECT id FROM area WHERE code='310100')),
('310106', '静安区', 3, (SELECT id FROM area WHERE code='310100')),
('310107', '普陀区', 3, (SELECT id FROM area WHERE code='310100')),
('310109', '虹口区', 3, (SELECT id FROM area WHERE code='310100')),
('310110', '杨浦区', 3, (SELECT id FROM area WHERE code='310100')),
('310112', '闵行区', 3, (SELECT id FROM area WHERE code='310100')),
('310113', '宝山区', 3, (SELECT id FROM area WHERE code='310100')),
('310114', '嘉定区', 3, (SELECT id FROM area WHERE code='310100')),
('310115', '浦东新区', 3, (SELECT id FROM area WHERE code='310100')),
('310116', '金山区', 3, (SELECT id FROM area WHERE code='310100')),
('310117', '松江区', 3, (SELECT id FROM area WHERE code='310100')),
('310118', '青浦区', 3, (SELECT id FROM area WHERE code='310100')),
('310120', '奉贤区', 3, (SELECT id FROM area WHERE code='310100')),
('310151', '崇明区', 3, (SELECT id FROM area WHERE code='310100'));

-- 天津市下属区
INSERT INTO area (code, name, level, parent_id) VALUES
('120100', '天津市', 2, (SELECT id FROM area WHERE code='120000')),
('120101', '和平区', 3, (SELECT id FROM area WHERE code='120100')),
('120102', '河东区', 3, (SELECT id FROM area WHERE code='120100')),
('120103', '河西区', 3, (SELECT id FROM area WHERE code='120100')),
('120104', '南开区', 3, (SELECT id FROM area WHERE code='120100')),
('120105', '河北区', 3, (SELECT id FROM area WHERE code='120100')),
('120106', '红桥区', 3, (SELECT id FROM area WHERE code='120100')),
('120110', '东丽区', 3, (SELECT id FROM area WHERE code='120100')),
('120111', '西青区', 3, (SELECT id FROM area WHERE code='120100')),
('120112', '津南区', 3, (SELECT id FROM area WHERE code='120100')),
('120113', '北辰区', 3, (SELECT id FROM area WHERE code='120100')),
('120114', '武清区', 3, (SELECT id FROM area WHERE code='120100')),
('120115', '宝坻区', 3, (SELECT id FROM area WHERE code='120100')),
('120116', '滨海新区', 3, (SELECT id FROM area WHERE code='120100')),
('120117', '宁河区', 3, (SELECT id FROM area WHERE code='120100')),
('120118', '静海区', 3, (SELECT id FROM area WHERE code='120100')),
('120119', '蓟州区', 3, (SELECT id FROM area WHERE code='120100'));

-- 重庆市下属区县
INSERT INTO area (code, name, level, parent_id) VALUES
('500100', '重庆市', 2, (SELECT id FROM area WHERE code='500000')),
('500101', '万州区', 3, (SELECT id FROM area WHERE code='500100')),
('500102', '涪陵区', 3, (SELECT id FROM area WHERE code='500100')),
('500103', '渝中区', 3, (SELECT id FROM area WHERE code='500100')),
('500104', '大渡口区', 3, (SELECT id FROM area WHERE code='500100')),
('500105', '江北区', 3, (SELECT id FROM area WHERE code='500100')),
('500106', '沙坪坝区', 3, (SELECT id FROM area WHERE code='500100')),
('500107', '九龙坡区', 3, (SELECT id FROM area WHERE code='500100')),
('500108', '南岸区', 3, (SELECT id FROM area WHERE code='500100')),
('500109', '北碚区', 3, (SELECT id FROM area WHERE code='500100')),
('500110', '綦江区', 3, (SELECT id FROM area WHERE code='500100')),
('500111', '大足区', 3, (SELECT id FROM area WHERE code='500100')),
('500112', '渝北区', 3, (SELECT id FROM area WHERE code='500100')),
('500113', '巴南区', 3, (SELECT id FROM area WHERE code='500100')),
('500114', '黔江区', 3, (SELECT id FROM area WHERE code='500100')),
('500115', '长寿区', 3, (SELECT id FROM area WHERE code='500100')),
('500116', '江津区', 3, (SELECT id FROM area WHERE code='500100')),
('500117', '合川区', 3, (SELECT id FROM area WHERE code='500100')),
('500118', '永川区', 3, (SELECT id FROM area WHERE code='500100')),
('500119', '南川区', 3, (SELECT id FROM area WHERE code='500100')),
('500120', '璧山区', 3, (SELECT id FROM area WHERE code='500100')),
('500151', '铜梁区', 3, (SELECT id FROM area WHERE code='500100')),
('500152', '潼南区', 3, (SELECT id FROM area WHERE code='500100')),
('500153', '荣昌区', 3, (SELECT id FROM area WHERE code='500100')),
('500154', '开州区', 3, (SELECT id FROM area WHERE code='500100')),
('500155', '梁平区', 3, (SELECT id FROM area WHERE code='500100')),
('500156', '武隆区', 3, (SELECT id FROM area WHERE code='500100'));

-- 河北省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('130100', '石家庄市', 2, (SELECT id FROM area WHERE code='130000')),
('130200', '唐山市', 2, (SELECT id FROM area WHERE code='130000')),
('130300', '秦皇岛市', 2, (SELECT id FROM area WHERE code='130000')),
('130400', '邯郸市', 2, (SELECT id FROM area WHERE code='130000')),
('130500', '邢台市', 2, (SELECT id FROM area WHERE code='130000')),
('130600', '保定市', 2, (SELECT id FROM area WHERE code='130000')),
('130700', '张家口市', 2, (SELECT id FROM area WHERE code='130000')),
('130800', '承德市', 2, (SELECT id FROM area WHERE code='130000')),
('130900', '沧州市', 2, (SELECT id FROM area WHERE code='130000')),
('131000', '廊坊市', 2, (SELECT id FROM area WHERE code='130000')),
('131100', '衡水市', 2, (SELECT id FROM area WHERE code='130000')),
-- 石家庄市区
('130102', '长安区', 3, (SELECT id FROM area WHERE code='130100')),
('130104', '桥西区', 3, (SELECT id FROM area WHERE code='130100')),
('130105', '新华区', 3, (SELECT id FROM area WHERE code='130100')),
('130107', '井陉矿区', 3, (SELECT id FROM area WHERE code='130100')),
('130108', '裕华区', 3, (SELECT id FROM area WHERE code='130100')),
('130109', '藁城区', 3, (SELECT id FROM area WHERE code='130100')),
('130110', '鹿泉区', 3, (SELECT id FROM area WHERE code='130100')),
('130111', '栾城区', 3, (SELECT id FROM area WHERE code='130100'));

-- 江苏省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('320100', '南京市', 2, (SELECT id FROM area WHERE code='320000')),
('320200', '无锡市', 2, (SELECT id FROM area WHERE code='320000')),
('320300', '徐州市', 2, (SELECT id FROM area WHERE code='320000')),
('320400', '常州市', 2, (SELECT id FROM area WHERE code='320000')),
('320500', '苏州市', 2, (SELECT id FROM area WHERE code='320000')),
('320600', '南通市', 2, (SELECT id FROM area WHERE code='320000')),
('320700', '连云港市', 2, (SELECT id FROM area WHERE code='320000')),
('320800', '淮安市', 2, (SELECT id FROM area WHERE code='320000')),
('320900', '盐城市', 2, (SELECT id FROM area WHERE code='320000')),
('321000', '扬州市', 2, (SELECT id FROM area WHERE code='320000')),
('321100', '镇江市', 2, (SELECT id FROM area WHERE code='320000')),
('321200', '泰州市', 2, (SELECT id FROM area WHERE code='320000')),
('321300', '宿迁市', 2, (SELECT id FROM area WHERE code='320000')),
-- 南京市区
('320102', '玄武区', 3, (SELECT id FROM area WHERE code='320100')),
('320104', '秦淮区', 3, (SELECT id FROM area WHERE code='320100')),
('320105', '建邺区', 3, (SELECT id FROM area WHERE code='320100')),
('320106', '鼓楼区', 3, (SELECT id FROM area WHERE code='320100')),
('320111', '浦口区', 3, (SELECT id FROM area WHERE code='320100')),
('320113', '栖霞区', 3, (SELECT id FROM area WHERE code='320100')),
('320114', '雨花台区', 3, (SELECT id FROM area WHERE code='320100')),
('320115', '江宁区', 3, (SELECT id FROM area WHERE code='320100')),
('320116', '六合区', 3, (SELECT id FROM area WHERE code='320100')),
('320117', '溧水区', 3, (SELECT id FROM area WHERE code='320100')),
('320118', '高淳区', 3, (SELECT id FROM area WHERE code='320100')),
-- 苏州市区
('320505', '虎丘区', 3, (SELECT id FROM area WHERE code='320500')),
('320506', '吴中区', 3, (SELECT id FROM area WHERE code='320500')),
('320507', '相城区', 3, (SELECT id FROM area WHERE code='320500')),
('320508', '姑苏区', 3, (SELECT id FROM area WHERE code='320500')),
('320509', '吴江区', 3, (SELECT id FROM area WHERE code='320500'));

-- 浙江省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('330100', '杭州市', 2, (SELECT id FROM area WHERE code='330000')),
('330200', '宁波市', 2, (SELECT id FROM area WHERE code='330000')),
('330300', '温州市', 2, (SELECT id FROM area WHERE code='330000')),
('330400', '嘉兴市', 2, (SELECT id FROM area WHERE code='330000')),
('330500', '湖州市', 2, (SELECT id FROM area WHERE code='330000')),
('330600', '绍兴市', 2, (SELECT id FROM area WHERE code='330000')),
('330700', '金华市', 2, (SELECT id FROM area WHERE code='330000')),
('330800', '衢州市', 2, (SELECT id FROM area WHERE code='330000')),
('330900', '舟山市', 2, (SELECT id FROM area WHERE code='330000')),
('331000', '台州市', 2, (SELECT id FROM area WHERE code='330000')),
('331100', '丽水市', 2, (SELECT id FROM area WHERE code='330000')),
-- 杭州市区
('330102', '上城区', 3, (SELECT id FROM area WHERE code='330100')),
('330105', '拱墅区', 3, (SELECT id FROM area WHERE code='330100')),
('330106', '西湖区', 3, (SELECT id FROM area WHERE code='330100')),
('330108', '滨江区', 3, (SELECT id FROM area WHERE code='330100')),
('330109', '萧山区', 3, (SELECT id FROM area WHERE code='330100')),
('330110', '余杭区', 3, (SELECT id FROM area WHERE code='330100')),
('330111', '富阳区', 3, (SELECT id FROM area WHERE code='330100')),
('330112', '临平区', 3, (SELECT id FROM area WHERE code='330100')),
('330113', '钱塘区', 3, (SELECT id FROM area WHERE code='330100')),
('330114', '临安区', 3, (SELECT id FROM area WHERE code='330100'));

-- 广东省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('440100', '广州市', 2, (SELECT id FROM area WHERE code='440000')),
('440200', '韶关市', 2, (SELECT id FROM area WHERE code='440000')),
('440300', '深圳市', 2, (SELECT id FROM area WHERE code='440000')),
('440400', '珠海市', 2, (SELECT id FROM area WHERE code='440000')),
('440500', '汕头市', 2, (SELECT id FROM area WHERE code='440000')),
('440600', '佛山市', 2, (SELECT id FROM area WHERE code='440000')),
('440700', '江门市', 2, (SELECT id FROM area WHERE code='440000')),
('440800', '茂名市', 2, (SELECT id FROM area WHERE code='440000')),
('440900', '肇庆市', 2, (SELECT id FROM area WHERE code='440000')),
('441200', '惠州市', 2, (SELECT id FROM area WHERE code='440000')),
('441300', '梅州市', 2, (SELECT id FROM area WHERE code='440000')),
('441400', '汕尾市', 2, (SELECT id FROM area WHERE code='440000')),
('441500', '河源市', 2, (SELECT id FROM area WHERE code='440000')),
('441600', '阳江市', 2, (SELECT id FROM area WHERE code='440000')),
('441700', '清远市', 2, (SELECT id FROM area WHERE code='440000')),
('441800', '东莞市', 2, (SELECT id FROM area WHERE code='440000')),
('441900', '中山市', 2, (SELECT id FROM area WHERE code='440000')),
('442000', '潮州市', 2, (SELECT id FROM area WHERE code='440000')),
('442100', '揭阳市', 2, (SELECT id FROM area WHERE code='440000')),
('442200', '云浮市', 2, (SELECT id FROM area WHERE code='440000')),
-- 广州市区
('440103', '荔湾区', 3, (SELECT id FROM area WHERE code='440100')),
('440104', '越秀区', 3, (SELECT id FROM area WHERE code='440100')),
('440105', '海珠区', 3, (SELECT id FROM area WHERE code='440100')),
('440106', '天河区', 3, (SELECT id FROM area WHERE code='440100')),
('440111', '白云区', 3, (SELECT id FROM area WHERE code='440100')),
('440112', '黄埔区', 3, (SELECT id FROM area WHERE code='440100')),
('440113', '番禺区', 3, (SELECT id FROM area WHERE code='440100')),
('440114', '花都区', 3, (SELECT id FROM area WHERE code='440100')),
('440115', '南沙区', 3, (SELECT id FROM area WHERE code='440100')),
('440117', '从化区', 3, (SELECT id FROM area WHERE code='440100')),
('440118', '增城区', 3, (SELECT id FROM area WHERE code='440100')),
-- 深圳市区
('440303', '罗湖区', 3, (SELECT id FROM area WHERE code='440300')),
('440304', '福田区', 3, (SELECT id FROM area WHERE code='440300')),
('440305', '南山区', 3, (SELECT id FROM area WHERE code='440300')),
('440306', '宝安区', 3, (SELECT id FROM area WHERE code='440300')),
('440307', '龙岗区', 3, (SELECT id FROM area WHERE code='440300')),
('440308', '盐田区', 3, (SELECT id FROM area WHERE code='440300')),
('440309', '龙华区', 3, (SELECT id FROM area WHERE code='440300')),
('440310', '坪山区', 3, (SELECT id FROM area WHERE code='440300')),
('440311', '光明区', 3, (SELECT id FROM area WHERE code='440300'));

-- 山东省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('370100', '济南市', 2, (SELECT id FROM area WHERE code='370000')),
('370200', '青岛市', 2, (SELECT id FROM area WHERE code='370000')),
('370300', '淄博市', 2, (SELECT id FROM area WHERE code='370000')),
('370400', '枣庄市', 2, (SELECT id FROM area WHERE code='370000')),
('370500', '东营市', 2, (SELECT id FROM area WHERE code='370000')),
('370600', '烟台市', 2, (SELECT id FROM area WHERE code='370000')),
('370700', '潍坊市', 2, (SELECT id FROM area WHERE code='370000')),
('370800', '济宁市', 2, (SELECT id FROM area WHERE code='370000')),
('370900', '泰安市', 2, (SELECT id FROM area WHERE code='370000')),
('371000', '威海市', 2, (SELECT id FROM area WHERE code='370000')),
('371100', '日照市', 2, (SELECT id FROM area WHERE code='370000')),
('371300', '临沂市', 2, (SELECT id FROM area WHERE code='370000')),
('371400', '德州市', 2, (SELECT id FROM area WHERE code='370000')),
('371500', '聊城市', 2, (SELECT id FROM area WHERE code='370000')),
('371600', '滨州市', 2, (SELECT id FROM area WHERE code='370000')),
('371700', '菏泽市', 2, (SELECT id FROM area WHERE code='370000'));

-- 湖北省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('420100', '武汉市', 2, (SELECT id FROM area WHERE code='420000')),
('420200', '黄石市', 2, (SELECT id FROM area WHERE code='420000')),
('420300', '十堰市', 2, (SELECT id FROM area WHERE code='420000')),
('420500', '宜昌市', 2, (SELECT id FROM area WHERE code='420000')),
('420600', '襄阳市', 2, (SELECT id FROM area WHERE code='420000')),
('420700', '鄂州市', 2, (SELECT id FROM area WHERE code='420000')),
('420800', '荆门市', 2, (SELECT id FROM area WHERE code='420000')),
('420900', '孝感市', 2, (SELECT id FROM area WHERE code='420000')),
('421000', '荆州市', 2, (SELECT id FROM area WHERE code='420000')),
('421100', '黄冈市', 2, (SELECT id FROM area WHERE code='420000')),
('421200', '咸宁市', 2, (SELECT id FROM area WHERE code='420000')),
('421300', '随州市', 2, (SELECT id FROM area WHERE code='420000')),
('422800', '恩施土家族苗族自治州', 2, (SELECT id FROM area WHERE code='420000'));

-- 湖南省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('430100', '长沙市', 2, (SELECT id FROM area WHERE code='430000')),
('430200', '株洲市', 2, (SELECT id FROM area WHERE code='430000')),
('430300', '湘潭市', 2, (SELECT id FROM area WHERE code='430000')),
('430400', '衡阳市', 2, (SELECT id FROM area WHERE code='430000')),
('430500', '邵阳市', 2, (SELECT id FROM area WHERE code='430000')),
('430600', '岳阳市', 2, (SELECT id FROM area WHERE code='430000')),
('430700', '常德市', 2, (SELECT id FROM area WHERE code='430000')),
('430800', '张家界市', 2, (SELECT id FROM area WHERE code='430000')),
('430900', '益阳市', 2, (SELECT id FROM area WHERE code='430000')),
('431000', '郴州市', 2, (SELECT id FROM area WHERE code='430000')),
('431100', '永州市', 2, (SELECT id FROM area WHERE code='430000')),
('431200', '怀化市', 2, (SELECT id FROM area WHERE code='430000')),
('431300', '娄底市', 2, (SELECT id FROM area WHERE code='430000')),
('433100', '湘西土家族苗族自治州', 2, (SELECT id FROM area WHERE code='430000'));

-- 四川省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('510100', '成都市', 2, (SELECT id FROM area WHERE code='510000')),
('510300', '自贡市', 2, (SELECT id FROM area WHERE code='510000')),
('510400', '攀枝花市', 2, (SELECT id FROM area WHERE code='510000')),
('510500', '泸州市', 2, (SELECT id FROM area WHERE code='510000')),
('510600', '德阳市', 2, (SELECT id FROM area WHERE code='510000')),
('510700', '绵阳市', 2, (SELECT id FROM area WHERE code='510000')),
('510800', '广元市', 2, (SELECT id FROM area WHERE code='510000')),
('510900', '遂宁市', 2, (SELECT id FROM area WHERE code='510000')),
('511000', '内江市', 2, (SELECT id FROM area WHERE code='510000')),
('511100', '乐山市', 2, (SELECT id FROM area WHERE code='510000')),
('511300', '南充市', 2, (SELECT id FROM area WHERE code='510000')),
('511400', '眉山市', 2, (SELECT id FROM area WHERE code='510000')),
('511500', '宜宾市', 2, (SELECT id FROM area WHERE code='510000')),
('511600', '广安市', 2, (SELECT id FROM area WHERE code='510000')),
('511700', '达州市', 2, (SELECT id FROM area WHERE code='510000')),
('511800', '雅安市', 2, (SELECT id FROM area WHERE code='510000')),
('511900', '巴中市', 2, (SELECT id FROM area WHERE code='510000')),
('512000', '资阳市', 2, (SELECT id FROM area WHERE code='510000')),
('513200', '阿坝藏族羌族自治州', 2, (SELECT id FROM area WHERE code='510000')),
('513300', '甘孜藏族自治州', 2, (SELECT id FROM area WHERE code='510000')),
('513400', '凉山彝族自治州', 2, (SELECT id FROM area WHERE code='510000'));

-- 福建省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('350100', '福州市', 2, (SELECT id FROM area WHERE code='350000')),
('350200', '厦门市', 2, (SELECT id FROM area WHERE code='350000')),
('350300', '莆田市', 2, (SELECT id FROM area WHERE code='350000')),
('350400', '三明市', 2, (SELECT id FROM area WHERE code='350000')),
('350500', '泉州市', 2, (SELECT id FROM area WHERE code='350000')),
('350600', '漳州市', 2, (SELECT id FROM area WHERE code='350000')),
('350700', '南平市', 2, (SELECT id FROM area WHERE code='350000')),
('350800', '龙岩市', 2, (SELECT id FROM area WHERE code='350000')),
('350900', '宁德市', 2, (SELECT id FROM area WHERE code='350000'));

-- 陕西省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('610100', '西安市', 2, (SELECT id FROM area WHERE code='610000')),
('610200', '铜川市', 2, (SELECT id FROM area WHERE code='610000')),
('610300', '宝鸡市', 2, (SELECT id FROM area WHERE code='610000')),
('610400', '咸阳市', 2, (SELECT id FROM area WHERE code='610000')),
('610500', '渭南市', 2, (SELECT id FROM area WHERE code='610000')),
('610600', '延安市', 2, (SELECT id FROM area WHERE code='610000')),
('610700', '汉中市', 2, (SELECT id FROM area WHERE code='610000')),
('610800', '榆林市', 2, (SELECT id FROM area WHERE code='610000')),
('610900', '安康市', 2, (SELECT id FROM area WHERE code='610000')),
('611000', '商洛市', 2, (SELECT id FROM area WHERE code='610000'));

-- 辽宁省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('210100', '沈阳市', 2, (SELECT id FROM area WHERE code='210000')),
('210200', '大连市', 2, (SELECT id FROM area WHERE code='210000')),
('210300', '鞍山市', 2, (SELECT id FROM area WHERE code='210000')),
('210400', '抚顺市', 2, (SELECT id FROM area WHERE code='210000')),
('210500', '本溪市', 2, (SELECT id FROM area WHERE code='210000')),
('210600', '丹东市', 2, (SELECT id FROM area WHERE code='210000')),
('210700', '锦州市', 2, (SELECT id FROM area WHERE code='210000')),
('210800', '营口市', 2, (SELECT id FROM area WHERE code='210000')),
('210900', '阜新市', 2, (SELECT id FROM area WHERE code='210000')),
('211000', '辽阳市', 2, (SELECT id FROM area WHERE code='210000')),
('211100', '盘锦市', 2, (SELECT id FROM area WHERE code='210000')),
('211200', '铁岭市', 2, (SELECT id FROM area WHERE code='210000')),
('211300', '朝阳市', 2, (SELECT id FROM area WHERE code='210000')),
('211400', '葫芦岛市', 2, (SELECT id FROM area WHERE code='210000'));

-- 吉林省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('220100', '长春市', 2, (SELECT id FROM area WHERE code='220000')),
('220200', '吉林市', 2, (SELECT id FROM area WHERE code='220000')),
('220300', '四平市', 2, (SELECT id FROM area WHERE code='220000')),
('220400', '辽源市', 2, (SELECT id FROM area WHERE code='220000')),
('220500', '通化市', 2, (SELECT id FROM area WHERE code='220000')),
('220600', '白山市', 2, (SELECT id FROM area WHERE code='220000')),
('220700', '松原市', 2, (SELECT id FROM area WHERE code='220000')),
('220800', '白城市', 2, (SELECT id FROM area WHERE code='220000')),
('222400', '延边朝鲜族自治州', 2, (SELECT id FROM area WHERE code='220000'));

-- 黑龙江省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('230100', '哈尔滨市', 2, (SELECT id FROM area WHERE code='230000')),
('230200', '齐齐哈尔市', 2, (SELECT id FROM area WHERE code='230000')),
('230300', '鸡西市', 2, (SELECT id FROM area WHERE code='230000')),
('230400', '鹤岗市', 2, (SELECT id FROM area WHERE code='230000')),
('230500', '双鸭山市', 2, (SELECT id FROM area WHERE code='230000')),
('230600', '大庆市', 2, (SELECT id FROM area WHERE code='230000')),
('230700', '伊春市', 2, (SELECT id FROM area WHERE code='230000')),
('230800', '佳木斯市', 2, (SELECT id FROM area WHERE code='230000')),
('230900', '七台河市', 2, (SELECT id FROM area WHERE code='230000')),
('231000', '牡丹江市', 2, (SELECT id FROM area WHERE code='230000')),
('231100', '黑河市', 2, (SELECT id FROM area WHERE code='230000')),
('231200', '绥化市', 2, (SELECT id FROM area WHERE code='230000')),
('232700', '大兴安岭地区', 2, (SELECT id FROM area WHERE code='230000'));

-- 安徽省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('340100', '合肥市', 2, (SELECT id FROM area WHERE code='340000')),
('340200', '芜湖市', 2, (SELECT id FROM area WHERE code='340000')),
('340300', '蚌埠市', 2, (SELECT id FROM area WHERE code='340000')),
('340400', '淮南市', 2, (SELECT id FROM area WHERE code='340000')),
('340500', '马鞍山市', 2, (SELECT id FROM area WHERE code='340000')),
('340600', '淮北市', 2, (SELECT id FROM area WHERE code='340000')),
('340700', '铜陵市', 2, (SELECT id FROM area WHERE code='340000')),
('340800', '安庆市', 2, (SELECT id FROM area WHERE code='340000')),
('341000', '黄山市', 2, (SELECT id FROM area WHERE code='340000')),
('341100', '滁州市', 2, (SELECT id FROM area WHERE code='340000')),
('341200', '阜阳市', 2, (SELECT id FROM area WHERE code='340000')),
('341300', '宿州市', 2, (SELECT id FROM area WHERE code='340000')),
('341500', '六安市', 2, (SELECT id FROM area WHERE code='340000')),
('341600', '亳州市', 2, (SELECT id FROM area WHERE code='340000')),
('341700', '池州市', 2, (SELECT id FROM area WHERE code='340000')),
('341800', '宣城市', 2, (SELECT id FROM area WHERE code='340000'));

-- 河南省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('410100', '郑州市', 2, (SELECT id FROM area WHERE code='410000')),
('410200', '开封市', 2, (SELECT id FROM area WHERE code='410000')),
('410300', '洛阳市', 2, (SELECT id FROM area WHERE code='410000')),
('410400', '平顶山市', 2, (SELECT id FROM area WHERE code='410000')),
('410500', '安阳市', 2, (SELECT id FROM area WHERE code='410000')),
('410600', '鹤壁市', 2, (SELECT id FROM area WHERE code='410000')),
('410700', '新乡市', 2, (SELECT id FROM area WHERE code='410000')),
('410800', '焦作市', 2, (SELECT id FROM area WHERE code='410000')),
('410900', '濮阳市', 2, (SELECT id FROM area WHERE code='410000')),
('411000', '许昌市', 2, (SELECT id FROM area WHERE code='410000')),
('411100', '漯河市', 2, (SELECT id FROM area WHERE code='410000')),
('411200', '三门峡市', 2, (SELECT id FROM area WHERE code='410000')),
('411300', '南阳市', 2, (SELECT id FROM area WHERE code='410000')),
('411400', '商丘市', 2, (SELECT id FROM area WHERE code='410000')),
('411500', '信阳市', 2, (SELECT id FROM area WHERE code='410000')),
('411600', '周口市', 2, (SELECT id FROM area WHERE code='410000')),
('411700', '驻马店市', 2, (SELECT id FROM area WHERE code='410000')),
('419001', '济源市', 2, (SELECT id FROM area WHERE code='410000'));

-- 江西省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('360100', '南昌市', 2, (SELECT id FROM area WHERE code='360000')),
('360200', '景德镇市', 2, (SELECT id FROM area WHERE code='360000')),
('360300', '萍乡市', 2, (SELECT id FROM area WHERE code='360000')),
('360400', '九江市', 2, (SELECT id FROM area WHERE code='360000')),
('360500', '新余市', 2, (SELECT id FROM area WHERE code='360000')),
('360600', '鹰潭市', 2, (SELECT id FROM area WHERE code='360000')),
('360700', '赣州市', 2, (SELECT id FROM area WHERE code='360000')),
('360800', '吉安市', 2, (SELECT id FROM area WHERE code='360000')),
('360900', '宜春市', 2, (SELECT id FROM area WHERE code='360000')),
('361000', '抚州市', 2, (SELECT id FROM area WHERE code='360000')),
('361100', '上饶市', 2, (SELECT id FROM area WHERE code='360000'));

-- 广西各市
INSERT INTO area (code, name, level, parent_id) VALUES
('450100', '南宁市', 2, (SELECT id FROM area WHERE code='450000')),
('450200', '柳州市', 2, (SELECT id FROM area WHERE code='450000')),
('450300', '桂林市', 2, (SELECT id FROM area WHERE code='450000')),
('450400', '梧州市', 2, (SELECT id FROM area WHERE code='450000')),
('450500', '北海市', 2, (SELECT id FROM area WHERE code='450000')),
('450600', '防城港市', 2, (SELECT id FROM area WHERE code='450000')),
('450700', '钦州市', 2, (SELECT id FROM area WHERE code='450000')),
('450800', '贵港市', 2, (SELECT id FROM area WHERE code='450000')),
('450900', '玉林市', 2, (SELECT id FROM area WHERE code='450000')),
('451000', '百色市', 2, (SELECT id FROM area WHERE code='450000')),
('451100', '贺州市', 2, (SELECT id FROM area WHERE code='450000')),
('451200', '河池市', 2, (SELECT id FROM area WHERE code='450000')),
('451300', '来宾市', 2, (SELECT id FROM area WHERE code='450000')),
('451400', '崇左市', 2, (SELECT id FROM area WHERE code='450000'));

-- 海南省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('460100', '海口市', 2, (SELECT id FROM area WHERE code='460000')),
('460200', '三亚市', 2, (SELECT id FROM area WHERE code='460000')),
('460300', '三沙市', 2, (SELECT id FROM area WHERE code='460000')),
('460400', '儋州市', 2, (SELECT id FROM area WHERE code='460000'));

-- 贵州省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('520100', '贵阳市', 2, (SELECT id FROM area WHERE code='520000')),
('520200', '六盘水市', 2, (SELECT id FROM area WHERE code='520000')),
('520300', '遵义市', 2, (SELECT id FROM area WHERE code='520000')),
('520400', '安顺市', 2, (SELECT id FROM area WHERE code='520000')),
('520500', '毕节市', 2, (SELECT id FROM area WHERE code='520000')),
('520600', '铜仁市', 2, (SELECT id FROM area WHERE code='520000')),
('522300', '黔西南布依族苗族自治州', 2, (SELECT id FROM area WHERE code='520000')),
('522600', '黔东南苗族侗族自治州', 2, (SELECT id FROM area WHERE code='520000')),
('522700', '黔南布依族苗族自治州', 2, (SELECT id FROM area WHERE code='520000'));

-- 云南省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('530100', '昆明市', 2, (SELECT id FROM area WHERE code='530000')),
('530300', '曲靖市', 2, (SELECT id FROM area WHERE code='530000')),
('530400', '玉溪市', 2, (SELECT id FROM area WHERE code='530000')),
('530500', '保山市', 2, (SELECT id FROM area WHERE code='530000')),
('530600', '昭通市', 2, (SELECT id FROM area WHERE code='530000')),
('530700', '丽江市', 2, (SELECT id FROM area WHERE code='530000')),
('530800', '普洱市', 2, (SELECT id FROM area WHERE code='530000')),
('530900', '临沧市', 2, (SELECT id FROM area WHERE code='530000')),
('532300', '楚雄彝族自治州', 2, (SELECT id FROM area WHERE code='530000')),
('532500', '红河哈尼族彝族自治州', 2, (SELECT id FROM area WHERE code='530000')),
('532600', '文山壮族苗族自治州', 2, (SELECT id FROM area WHERE code='530000')),
('532800', '西双版纳傣族自治州', 2, (SELECT id FROM area WHERE code='530000')),
('532900', '大理白族自治州', 2, (SELECT id FROM area WHERE code='530000')),
('533100', '德宏傣族景颇族自治州', 2, (SELECT id FROM area WHERE code='530000')),
('533300', '怒江傈僳族自治州', 2, (SELECT id FROM area WHERE code='530000')),
('533400', '迪庆藏族自治州', 2, (SELECT id FROM area WHERE code='530000'));

-- 西藏各市
INSERT INTO area (code, name, level, parent_id) VALUES
('540100', '拉萨市', 2, (SELECT id FROM area WHERE code='540000')),
('540200', '日喀则市', 2, (SELECT id FROM area WHERE code='540000')),
('540300', '昌都市', 2, (SELECT id FROM area WHERE code='540000')),
('540400', '林芝市', 2, (SELECT id FROM area WHERE code='540000')),
('540500', '山南市', 2, (SELECT id FROM area WHERE code='540000')),
('540600', '那曲市', 2, (SELECT id FROM area WHERE code='540000')),
('542500', '阿里地区', 2, (SELECT id FROM area WHERE code='540000'));

-- 甘肃省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('620100', '兰州市', 2, (SELECT id FROM area WHERE code='620000')),
('620200', '嘉峪关市', 2, (SELECT id FROM area WHERE code='620000')),
('620300', '金昌市', 2, (SELECT id FROM area WHERE code='620000')),
('620400', '白银市', 2, (SELECT id FROM area WHERE code='620000')),
('620500', '天水市', 2, (SELECT id FROM area WHERE code='620000')),
('620600', '武威市', 2, (SELECT id FROM area WHERE code='620000')),
('620700', '张掖市', 2, (SELECT id FROM area WHERE code='620000')),
('620800', '平凉市', 2, (SELECT id FROM area WHERE code='620000')),
('620900', '酒泉市', 2, (SELECT id FROM area WHERE code='620000')),
('621000', '庆阳市', 2, (SELECT id FROM area WHERE code='620000')),
('621100', '定西市', 2, (SELECT id FROM area WHERE code='620000')),
('621200', '陇南市', 2, (SELECT id FROM area WHERE code='620000')),
('622900', '甘南藏族自治州', 2, (SELECT id FROM area WHERE code='620000')),
('623000', '临夏回族自治州', 2, (SELECT id FROM area WHERE code='620000'));

-- 青海省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('630100', '西宁市', 2, (SELECT id FROM area WHERE code='630000')),
('630200', '海东市', 2, (SELECT id FROM area WHERE code='630000')),
('632200', '海北藏族自治州', 2, (SELECT id FROM area WHERE code='630000')),
('632300', '黄南藏族自治州', 2, (SELECT id FROM area WHERE code='630000')),
('632500', '海南藏族自治州', 2, (SELECT id FROM area WHERE code='630000')),
('632600', '果洛藏族自治州', 2, (SELECT id FROM area WHERE code='630000')),
('632700', '玉树藏族自治州', 2, (SELECT id FROM area WHERE code='630000')),
('632800', '海西蒙古族藏族自治州', 2, (SELECT id FROM area WHERE code='630000'));

-- 宁夏各市
INSERT INTO area (code, name, level, parent_id) VALUES
('640100', '银川市', 2, (SELECT id FROM area WHERE code='640000')),
('640200', '石嘴山市', 2, (SELECT id FROM area WHERE code='640000')),
('640300', '吴忠市', 2, (SELECT id FROM area WHERE code='640000')),
('640400', '固原市', 2, (SELECT id FROM area WHERE code='640000')),
('640500', '中卫市', 2, (SELECT id FROM area WHERE code='640000'));

-- 新疆各市
INSERT INTO area (code, name, level, parent_id) VALUES
('650100', '乌鲁木齐市', 2, (SELECT id FROM area WHERE code='650000')),
('650200', '克拉玛依市', 2, (SELECT id FROM area WHERE code='650000')),
('650400', '吐鲁番市', 2, (SELECT id FROM area WHERE code='650000')),
('650500', '哈密市', 2, (SELECT id FROM area WHERE code='650000')),
('652300', '昌吉回族自治州', 2, (SELECT id FROM area WHERE code='650000')),
('652700', '博尔塔拉蒙古自治州', 2, (SELECT id FROM area WHERE code='650000')),
('652800', '巴音郭楞蒙古自治州', 2, (SELECT id FROM area WHERE code='650000')),
('652900', '阿克苏地区', 2, (SELECT id FROM area WHERE code='650000')),
('653000', '克孜勒苏柯尔克孜自治州', 2, (SELECT id FROM area WHERE code='650000')),
('653100', '喀什地区', 2, (SELECT id FROM area WHERE code='650000')),
('653200', '和田地区', 2, (SELECT id FROM area WHERE code='650000')),
('654000', '伊犁哈萨克自治州', 2, (SELECT id FROM area WHERE code='650000')),
('654200', '塔城地区', 2, (SELECT id FROM area WHERE code='650000')),
('654300', '阿勒泰地区', 2, (SELECT id FROM area WHERE code='650000'));

-- 内蒙古各市
INSERT INTO area (code, name, level, parent_id) VALUES
('150100', '呼和浩特市', 2, (SELECT id FROM area WHERE code='150000')),
('150200', '包头市', 2, (SELECT id FROM area WHERE code='150000')),
('150300', '乌海市', 2, (SELECT id FROM area WHERE code='150000')),
('150400', '赤峰市', 2, (SELECT id FROM area WHERE code='150000')),
('150500', '通辽市', 2, (SELECT id FROM area WHERE code='150000')),
('150600', '鄂尔多斯市', 2, (SELECT id FROM area WHERE code='150000')),
('150700', '呼伦贝尔市', 2, (SELECT id FROM area WHERE code='150000')),
('150800', '巴彦淖尔市', 2, (SELECT id FROM area WHERE code='150000')),
('150900', '乌兰察布市', 2, (SELECT id FROM area WHERE code='150000')),
('152200', '兴安盟', 2, (SELECT id FROM area WHERE code='150000')),
('152500', '锡林郭勒盟', 2, (SELECT id FROM area WHERE code='150000')),
('152900', '阿拉善盟', 2, (SELECT id FROM area WHERE code='150000'));

-- 山西省各市
INSERT INTO area (code, name, level, parent_id) VALUES
('140100', '太原市', 2, (SELECT id FROM area WHERE code='140000')),
('140200', '大同市', 2, (SELECT id FROM area WHERE code='140000')),
('140300', '阳泉市', 2, (SELECT id FROM area WHERE code='140000')),
('140400', '长治市', 2, (SELECT id FROM area WHERE code='140000')),
('140500', '晋城市', 2, (SELECT id FROM area WHERE code='140000')),
('140600', '朔州市', 2, (SELECT id FROM area WHERE code='140000')),
('140700', '晋中市', 2, (SELECT id FROM area WHERE code='140000')),
('140800', '运城市', 2, (SELECT id FROM area WHERE code='140000')),
('140900', '忻州市', 2, (SELECT id FROM area WHERE code='140000')),
('141000', '临汾市', 2, (SELECT id FROM area WHERE code='140000')),
('141100', '吕梁市', 2, (SELECT id FROM area WHERE code='140000'));

-- ==================== 区级数据补充 ====================
-- 成都市区
INSERT INTO area (code, name, level, parent_id) VALUES
('510104', '锦江区', 3, (SELECT id FROM area WHERE code='510100')),
('510105', '青羊区', 3, (SELECT id FROM area WHERE code='510100')),
('510106', '金牛区', 3, (SELECT id FROM area WHERE code='510100')),
('510107', '武侯区', 3, (SELECT id FROM area WHERE code='510100')),
('510108', '成华区', 3, (SELECT id FROM area WHERE code='510100')),
('510112', '龙泉驿区', 3, (SELECT id FROM area WHERE code='510100')),
('510113', '青白江区', 3, (SELECT id FROM area WHERE code='510100')),
('510114', '新都区', 3, (SELECT id FROM area WHERE code='510100')),
('510115', '温江区', 3, (SELECT id FROM area WHERE code='510100')),
('510116', '双流区', 3, (SELECT id FROM area WHERE code='510100')),
('510117', '郫都区', 3, (SELECT id FROM area WHERE code='510100')),
('510121', '金堂县', 3, (SELECT id FROM area WHERE code='510100')),
('510129', '大邑县', 3, (SELECT id FROM area WHERE code='510100')),
('510131', '蒲江县', 3, (SELECT id FROM area WHERE code='510100')),
('510132', '新津县', 3, (SELECT id FROM area WHERE code='510100')),
('510181', '都江堰市', 3, (SELECT id FROM area WHERE code='510100')),
('510182', '彭州市', 3, (SELECT id FROM area WHERE code='510100')),
('510183', '邛崃市', 3, (SELECT id FROM area WHERE code='510100')),
('510184', '崇州市', 3, (SELECT id FROM area WHERE code='510100')),
('510185', '简阳市', 3, (SELECT id FROM area WHERE code='510100'));

-- 武汉市区
INSERT INTO area (code, name, level, parent_id) VALUES
('420102', '江岸区', 3, (SELECT id FROM area WHERE code='420100')),
('420103', '江汉区', 3, (SELECT id FROM area WHERE code='420100')),
('420104', '硚口区', 3, (SELECT id FROM area WHERE code='420100')),
('420105', '汉阳区', 3, (SELECT id FROM area WHERE code='420100')),
('420106', '武昌区', 3, (SELECT id FROM area WHERE code='420100')),
('420107', '青山区', 3, (SELECT id FROM area WHERE code='420100')),
('420111', '洪山区', 3, (SELECT id FROM area WHERE code='420100')),
('420112', '东西湖区', 3, (SELECT id FROM area WHERE code='420100')),
('420113', '汉南区', 3, (SELECT id FROM area WHERE code='420100')),
('420114', '蔡甸区', 3, (SELECT id FROM area WHERE code='420100')),
('420115', '江夏区', 3, (SELECT id FROM area WHERE code='420100')),
('420116', '黄陂区', 3, (SELECT id FROM area WHERE code='420100')),
('420117', '新洲区', 3, (SELECT id FROM area WHERE code='420100'));

-- 长沙市区
INSERT INTO area (code, name, level, parent_id) VALUES
('430102', '芙蓉区', 3, (SELECT id FROM area WHERE code='430100')),
('430103', '天心区', 3, (SELECT id FROM area WHERE code='430100')),
('430104', '岳麓区', 3, (SELECT id FROM area WHERE code='430100')),
('430105', '开福区', 3, (SELECT id FROM area WHERE code='430100')),
('430111', '雨花区', 3, (SELECT id FROM area WHERE code='430100')),
('430112', '望城区', 3, (SELECT id FROM area WHERE code='430100')),
('430121', '长沙县', 3, (SELECT id FROM area WHERE code='430100')),
('430181', '浏阳市', 3, (SELECT id FROM area WHERE code='430100')),
('430182', '宁乡市', 3, (SELECT id FROM area WHERE code='430100'));

-- 西安市区
INSERT INTO area (code, name, level, parent_id) VALUES
('610102', '新城区', 3, (SELECT id FROM area WHERE code='610100')),
('610103', '碑林区', 3, (SELECT id FROM area WHERE code='610100')),
('610104', '莲湖区', 3, (SELECT id FROM area WHERE code='610100')),
('610111', '灞桥区', 3, (SELECT id FROM area WHERE code='610100')),
('610112', '未央区', 3, (SELECT id FROM area WHERE code='610100')),
('610113', '雁塔区', 3, (SELECT id FROM area WHERE code='610100')),
('610114', '阎良区', 3, (SELECT id FROM area WHERE code='610100')),
('610115', '临潼区', 3, (SELECT id FROM area WHERE code='610100')),
('610116', '长安区', 3, (SELECT id FROM area WHERE code='610100')),
('610117', '高陵区', 3, (SELECT id FROM area WHERE code='610100')),
('610118', '鄠邑区', 3, (SELECT id FROM area WHERE code='610100'));

-- 厦门市区
INSERT INTO area (code, name, level, parent_id) VALUES
('350203', '思明区', 3, (SELECT id FROM area WHERE code='350200')),
('350205', '海沧区', 3, (SELECT id FROM area WHERE code='350200')),
('350206', '湖里区', 3, (SELECT id FROM area WHERE code='350200')),
('350211', '集美区', 3, (SELECT id FROM area WHERE code='350200')),
('350212', '同安区', 3, (SELECT id FROM area WHERE code='350200')),
('350213', '翔安区', 3, (SELECT id FROM area WHERE code='350200'));

-- 郑州市区
INSERT INTO area (code, name, level, parent_id) VALUES
('410102', '中原区', 3, (SELECT id FROM area WHERE code='410100')),
('410103', '二七区', 3, (SELECT id FROM area WHERE code='410100')),
('410104', '管城回族区', 3, (SELECT id FROM area WHERE code='410100')),
('410105', '金水区', 3, (SELECT id FROM area WHERE code='410100')),
('410106', '上街区', 3, (SELECT id FROM area WHERE code='410100')),
('410108', '惠济区', 3, (SELECT id FROM area WHERE code='410100')),
('410122', '中牟县', 3, (SELECT id FROM area WHERE code='410100')),
('410181', '巩义市', 3, (SELECT id FROM area WHERE code='410100')),
('410182', '荥阳市', 3, (SELECT id FROM area WHERE code='410100')),
('410183', '新密市', 3, (SELECT id FROM area WHERE code='410100')),
('410184', '新郑市', 3, (SELECT id FROM area WHERE code='410100')),
('410185', '登封市', 3, (SELECT id FROM area WHERE code='410100'));

-- 南昌市区
INSERT INTO area (code, name, level, parent_id) VALUES
('360102', '东湖区', 3, (SELECT id FROM area WHERE code='360100')),
('360103', '西湖区', 3, (SELECT id FROM area WHERE code='360100')),
('360104', '青云谱区', 3, (SELECT id FROM area WHERE code='360100')),
('360111', '青山湖区', 3, (SELECT id FROM area WHERE code='360100')),
('360112', '新建区', 3, (SELECT id FROM area WHERE code='360100')),
('360113', '红谷滩区', 3, (SELECT id FROM area WHERE code='360100'));

-- 昆明市区
INSERT INTO area (code, name, level, parent_id) VALUES
('530102', '五华区', 3, (SELECT id FROM area WHERE code='530100')),
('530103', '盘龙区', 3, (SELECT id FROM area WHERE code='530100')),
('530111', '官渡区', 3, (SELECT id FROM area WHERE code='530100')),
('530112', '西山区', 3, (SELECT id FROM area WHERE code='530100')),
('530113', '东川区', 3, (SELECT id FROM area WHERE code='530100')),
('530114', '呈贡区', 3, (SELECT id FROM area WHERE code='530100'));

-- 贵阳市区
INSERT INTO area (code, name, level, parent_id) VALUES
('520102', '南明区', 3, (SELECT id FROM area WHERE code='520100')),
('520103', '云岩区', 3, (SELECT id FROM area WHERE code='520100')),
('520111', '花溪区', 3, (SELECT id FROM area WHERE code='520100')),
('520112', '乌当区', 3, (SELECT id FROM area WHERE code='520100')),
('520113', '白云区', 3, (SELECT id FROM area WHERE code='520100')),
('520115', '观山湖区', 3, (SELECT id FROM area WHERE code='520100'));

-- 乌鲁木齐市区
INSERT INTO area (code, name, level, parent_id) VALUES
('650102', '天山区', 3, (SELECT id FROM area WHERE code='650100')),
('650103', '沙依巴克区', 3, (SELECT id FROM area WHERE code='650100')),
('650104', '新市区', 3, (SELECT id FROM area WHERE code='650100')),
('650105', '水磨沟区', 3, (SELECT id FROM area WHERE code='650100')),
('650106', '头屯河区', 3, (SELECT id FROM area WHERE code='650100')),
('650107', '达坂城区', 3, (SELECT id FROM area WHERE code='650100')),
('650109', '米东区', 3, (SELECT id FROM area WHERE code='650100'));

-- 兰州市区
INSERT INTO area (code, name, level, parent_id) VALUES
('620102', '城关区', 3, (SELECT id FROM area WHERE code='620100')),
('620103', '七里河区', 3, (SELECT id FROM area WHERE code='620100')),
('620104', '西固区', 3, (SELECT id FROM area WHERE code='620100')),
('620105', '安宁区', 3, (SELECT id FROM area WHERE code='620100')),
('620111', '红古区', 3, (SELECT id FROM area WHERE code='620100'));

-- 呼和浩特市区
INSERT INTO area (code, name, level, parent_id) VALUES
('150102', '新城区', 3, (SELECT id FROM area WHERE code='150100')),
('150103', '回民区', 3, (SELECT id FROM area WHERE code='150100')),
('150104', '玉泉区', 3, (SELECT id FROM area WHERE code='150100')),
('150105', '赛罕区', 3, (SELECT id FROM area WHERE code='150100'));

-- 太原市区
INSERT INTO area (code, name, level, parent_id) VALUES
('140105', '小店区', 3, (SELECT id FROM area WHERE code='140100')),
('140106', '迎泽区', 3, (SELECT id FROM area WHERE code='140100')),
('140107', '杏花岭区', 3, (SELECT id FROM area WHERE code='140100')),
('140108', '尖草坪区', 3, (SELECT id FROM area WHERE code='140100')),
('140109', '万柏林区', 3, (SELECT id FROM area WHERE code='140100')),
('140110', '晋源区', 3, (SELECT id FROM area WHERE code='140100'));

-- 沈阳市区
INSERT INTO area (code, name, level, parent_id) VALUES
('210102', '和平区', 3, (SELECT id FROM area WHERE code='210100')),
('210103', '沈河区', 3, (SELECT id FROM area WHERE code='210100')),
('210104', '大东区', 3, (SELECT id FROM area WHERE code='210100')),
('210105', '皇姑区', 3, (SELECT id FROM area WHERE code='210100')),
('210106', '铁西区', 3, (SELECT id FROM area WHERE code='210100')),
('210111', '苏家屯区', 3, (SELECT id FROM area WHERE code='210100')),
('210112', '浑南区', 3, (SELECT id FROM area WHERE code='210100')),
('210113', '沈北新区', 3, (SELECT id FROM area WHERE code='210100')),
('210114', '于洪区', 3, (SELECT id FROM area WHERE code='210100'));

-- 大连市区
INSERT INTO area (code, name, level, parent_id) VALUES
('210202', '中山区', 3, (SELECT id FROM area WHERE code='210200')),
('210203', '西岗区', 3, (SELECT id FROM area WHERE code='210200')),
('210204', '沙河口区', 3, (SELECT id FROM area WHERE code='210200')),
('210211', '甘井子区', 3, (SELECT id FROM area WHERE code='210200')),
('210212', '旅顺口区', 3, (SELECT id FROM area WHERE code='210200')),
('210213', '金州区', 3, (SELECT id FROM area WHERE code='210200'));

-- 长春市区
INSERT INTO area (code, name, level, parent_id) VALUES
('220102', '南关区', 3, (SELECT id FROM area WHERE code='220100')),
('220103', '宽城区', 3, (SELECT id FROM area WHERE code='220100')),
('220104', '朝阳区', 3, (SELECT id FROM area WHERE code='220100')),
('220105', '二道区', 3, (SELECT id FROM area WHERE code='220100')),
('220106', '绿园区', 3, (SELECT id FROM area WHERE code='220100')),
('220112', '双阳区', 3, (SELECT id FROM area WHERE code='220100')),
('220113', '九台区', 3, (SELECT id FROM area WHERE code='220100'));

-- 哈尔滨市区
INSERT INTO area (code, name, level, parent_id) VALUES
('230102', '道里区', 3, (SELECT id FROM area WHERE code='230100')),
('230103', '南岗区', 3, (SELECT id FROM area WHERE code='230100')),
('230104', '道外区', 3, (SELECT id FROM area WHERE code='230100')),
('230108', '平房区', 3, (SELECT id FROM area WHERE code='230100')),
('230109', '松北区', 3, (SELECT id FROM area WHERE code='230100')),
('230110', '香坊区', 3, (SELECT id FROM area WHERE code='230100')),
('230111', '呼兰区', 3, (SELECT id FROM area WHERE code='230100')),
('230112', '阿城区', 3, (SELECT id FROM area WHERE code='230100')),
('230113', '双城区', 3, (SELECT id FROM area WHERE code='230100'));

-- 合肥市区
INSERT INTO area (code, name, level, parent_id) VALUES
('340102', '瑶海区', 3, (SELECT id FROM area WHERE code='340100')),
('340103', '庐阳区', 3, (SELECT id FROM area WHERE code='340100')),
('340104', '蜀山区', 3, (SELECT id FROM area WHERE code='340100')),
('340111', '包河区', 3, (SELECT id FROM area WHERE code='340100'));

-- 福州市区
INSERT INTO area (code, name, level, parent_id) VALUES
('350102', '鼓楼区', 3, (SELECT id FROM area WHERE code='350100')),
('350103', '台江区', 3, (SELECT id FROM area WHERE code='350100')),
('350104', '仓山区', 3, (SELECT id FROM area WHERE code='350100')),
('350105', '马尾区', 3, (SELECT id FROM area WHERE code='350100')),
('350111', '晋安区', 3, (SELECT id FROM area WHERE code='350100')),
('350112', '长乐区', 3, (SELECT id FROM area WHERE code='350100'));

-- 南宁市区
INSERT INTO area (code, name, level, parent_id) VALUES
('450102', '兴宁区', 3, (SELECT id FROM area WHERE code='450100')),
('450103', '青秀区', 3, (SELECT id FROM area WHERE code='450100')),
('450105', '江南区', 3, (SELECT id FROM area WHERE code='450100')),
('450107', '西乡塘区', 3, (SELECT id FROM area WHERE code='450100')),
('450108', '良庆区', 3, (SELECT id FROM area WHERE code='450100')),
('450109', '邕宁区', 3, (SELECT id FROM area WHERE code='450100'));

-- 海口市区
INSERT INTO area (code, name, level, parent_id) VALUES
('460105', '秀英区', 3, (SELECT id FROM area WHERE code='460100')),
('460106', '龙华区', 3, (SELECT id FROM area WHERE code='460100')),
('460107', '琼山区', 3, (SELECT id FROM area WHERE code='460100')),
('460108', '美兰区', 3, (SELECT id FROM area WHERE code='460100'));

-- 三亚市区
INSERT INTO area (code, name, level, parent_id) VALUES
('460202', '海棠区', 3, (SELECT id FROM area WHERE code='460200')),
('460203', '吉阳区', 3, (SELECT id FROM area WHERE code='460200')),
('460204', '天涯区', 3, (SELECT id FROM area WHERE code='460200')),
('460205', '崖州区', 3, (SELECT id FROM area WHERE code='460200'));

-- 拉萨市区
INSERT INTO area (code, name, level, parent_id) VALUES
('540102', '城关区', 3, (SELECT id FROM area WHERE code='540100')),
('540103', '堆龙德庆区', 3, (SELECT id FROM area WHERE code='540100')),
('540104', '达孜区', 3, (SELECT id FROM area WHERE code='540100'));

-- 西宁市区
INSERT INTO area (code, name, level, parent_id) VALUES
('630102', '城东区', 3, (SELECT id FROM area WHERE code='630100')),
('630103', '城中区', 3, (SELECT id FROM area WHERE code='630100')),
('630104', '城西区', 3, (SELECT id FROM area WHERE code='630100')),
('630105', '城北区', 3, (SELECT id FROM area WHERE code='630100'));

-- 银川市区
INSERT INTO area (code, name, level, parent_id) VALUES
('640104', '兴庆区', 3, (SELECT id FROM area WHERE code='640100')),
('640105', '西夏区', 3, (SELECT id FROM area WHERE code='640100')),
('640106', '金凤区', 3, (SELECT id FROM area WHERE code='640100'));

-- 香港特别行政区
INSERT INTO area (code, name, level, parent_id) VALUES
('810100', '香港岛', 2, (SELECT id FROM area WHERE code='810000')),
('810200', '九龙', 2, (SELECT id FROM area WHERE code='810000')),
('810300', '新界', 2, (SELECT id FROM area WHERE code='810000'));

-- 澳门特别行政区
INSERT INTO area (code, name, level, parent_id) VALUES
('820100', '澳门半岛', 2, (SELECT id FROM area WHERE code='820000')),
('820200', '氹仔', 2, (SELECT id FROM area WHERE code='820000')),
('820300', '路环', 2, (SELECT id FROM area WHERE code='820000'));

-- 台湾省主要城市
INSERT INTO area (code, name, level, parent_id) VALUES
('710100', '台北市', 2, (SELECT id FROM area WHERE code='710000')),
('710200', '高雄市', 2, (SELECT id FROM area WHERE code='710000')),
('710300', '台南市', 2, (SELECT id FROM area WHERE code='710000')),
('710400', '新北市', 2, (SELECT id FROM area WHERE code='710000')),
('710500', '桃园市', 2, (SELECT id FROM area WHERE code='710000')),
('710600', '台中市', 2, (SELECT id FROM area WHERE code='710000'));