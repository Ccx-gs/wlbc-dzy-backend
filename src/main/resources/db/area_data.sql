-- 省级数据
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

-- 北京市
INSERT INTO area (code, name, level, parent_id) SELECT '110100', '北京市', 2, id FROM area WHERE code='110000';
INSERT INTO area (code, name, level, parent_id) SELECT '110101', '东城区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110102', '西城区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110105', '朝阳区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110106', '丰台区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110107', '石景山区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110108', '海淀区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110109', '门头沟区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110111', '房山区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110112', '通州区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110113', '顺义区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110114', '昌平区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110115', '大兴区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110116', '怀柔区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110117', '平谷区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110118', '密云区', 3, id FROM area WHERE code='110100';
INSERT INTO area (code, name, level, parent_id) SELECT '110119', '延庆区', 3, id FROM area WHERE code='110100';

-- 上海市
INSERT INTO area (code, name, level, parent_id) SELECT '310100', '上海市', 2, id FROM area WHERE code='310000';
INSERT INTO area (code, name, level, parent_id) SELECT '310101', '黄浦区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310104', '徐汇区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310105', '长宁区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310106', '静安区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310107', '普陀区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310109', '虹口区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310110', '杨浦区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310112', '闵行区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310113', '宝山区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310114', '嘉定区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310115', '浦东新区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310116', '金山区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310117', '松江区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310118', '青浦区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310120', '奉贤区', 3, id FROM area WHERE code='310100';
INSERT INTO area (code, name, level, parent_id) SELECT '310151', '崇明区', 3, id FROM area WHERE code='310100';

-- 天津市
INSERT INTO area (code, name, level, parent_id) SELECT '120100', '天津市', 2, id FROM area WHERE code='120000';
INSERT INTO area (code, name, level, parent_id) SELECT '120101', '和平区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120102', '河东区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120103', '河西区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120104', '南开区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120105', '河北区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120106', '红桥区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120110', '东丽区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120111', '西青区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120112', '津南区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120113', '北辰区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120114', '武清区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120115', '宝坻区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120116', '滨海新区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120117', '宁河区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120118', '静海区', 3, id FROM area WHERE code='120100';
INSERT INTO area (code, name, level, parent_id) SELECT '120119', '蓟州区', 3, id FROM area WHERE code='120100';

-- 重庆市
INSERT INTO area (code, name, level, parent_id) SELECT '500100', '重庆市', 2, id FROM area WHERE code='500000';
INSERT INTO area (code, name, level, parent_id) SELECT '500101', '万州区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500102', '涪陵区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500103', '渝中区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500104', '大渡口区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500105', '江北区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500106', '沙坪坝区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500107', '九龙坡区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500108', '南岸区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500109', '北碚区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500110', '綦江区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500111', '大足区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500112', '渝北区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500113', '巴南区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500114', '黔江区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500115', '长寿区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500116', '江津区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500117', '合川区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500118', '永川区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500119', '南川区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500120', '璧山区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500151', '铜梁区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500152', '潼南区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500153', '荣昌区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500154', '开州区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500155', '梁平区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500156', '武隆区', 3, id FROM area WHERE code='500100';

-- 广东省各市
INSERT INTO area (code, name, level, parent_id) SELECT '440100', '广州市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '440200', '韶关市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '440300', '深圳市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '440400', '珠海市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '440500', '汕头市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '440600', '佛山市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '440700', '江门市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '440800', '茂名市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '440900', '肇庆市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '441200', '惠州市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '441300', '梅州市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '441400', '汕尾市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '441500', '河源市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '441600', '阳江市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '441700', '清远市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '441800', '东莞市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '441900', '中山市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '442000', '潮州市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '442100', '揭阳市', 2, id FROM area WHERE code='440000';
INSERT INTO area (code, name, level, parent_id) SELECT '442200', '云浮市', 2, id FROM area WHERE code='440000';

-- 广州市区
INSERT INTO area (code, name, level, parent_id) SELECT '440103', '荔湾区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440104', '越秀区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440105', '海珠区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440106', '天河区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440111', '白云区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440112', '黄埔区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440113', '番禺区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440114', '花都区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440115', '南沙区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440117', '从化区', 3, id FROM area WHERE code='440100';
INSERT INTO area (code, name, level, parent_id) SELECT '440118', '增城区', 3, id FROM area WHERE code='440100';

-- 深圳市区
INSERT INTO area (code, name, level, parent_id) SELECT '440303', '罗湖区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440304', '福田区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440305', '南山区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440306', '宝安区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440307', '龙岗区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440308', '盐田区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440309', '龙华区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440310', '坪山区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440311', '光明区', 3, id FROM area WHERE code='440300';

-- 江苏省各市
INSERT INTO area (code, name, level, parent_id) SELECT '320100', '南京市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '320200', '无锡市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '320300', '徐州市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '320400', '常州市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '320500', '苏州市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '320600', '南通市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '320700', '连云港市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '320800', '淮安市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '320900', '盐城市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '321000', '扬州市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '321100', '镇江市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '321200', '泰州市', 2, id FROM area WHERE code='320000';
INSERT INTO area (code, name, level, parent_id) SELECT '321300', '宿迁市', 2, id FROM area WHERE code='320000';

-- 南京市区
INSERT INTO area (code, name, level, parent_id) SELECT '320102', '玄武区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320104', '秦淮区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320105', '建邺区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320106', '鼓楼区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320111', '浦口区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320113', '栖霞区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320114', '雨花台区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320115', '江宁区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320116', '六合区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320117', '溧水区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320118', '高淳区', 3, id FROM area WHERE code='320100';

-- 苏州市区
INSERT INTO area (code, name, level, parent_id) SELECT '320505', '虎丘区', 3, id FROM area WHERE code='320500';
INSERT INTO area (code, name, level, parent_id) SELECT '320506', '吴中区', 3, id FROM area WHERE code='320500';
INSERT INTO area (code, name, level, parent_id) SELECT '320507', '相城区', 3, id FROM area WHERE code='320500';
INSERT INTO area (code, name, level, parent_id) SELECT '320508', '姑苏区', 3, id FROM area WHERE code='320500';
INSERT INTO area (code, name, level, parent_id) SELECT '320509', '吴江区', 3, id FROM area WHERE code='320500';

-- 浙江省各市
INSERT INTO area (code, name, level, parent_id) SELECT '330100', '杭州市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '330200', '宁波市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '330300', '温州市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '330400', '嘉兴市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '330500', '湖州市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '330600', '绍兴市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '330700', '金华市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '330800', '衢州市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '330900', '舟山市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '331000', '台州市', 2, id FROM area WHERE code='330000';
INSERT INTO area (code, name, level, parent_id) SELECT '331100', '丽水市', 2, id FROM area WHERE code='330000';

-- 杭州市区
INSERT INTO area (code, name, level, parent_id) SELECT '330102', '上城区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330105', '拱墅区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330106', '西湖区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330108', '滨江区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330109', '萧山区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330110', '余杭区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330111', '富阳区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330112', '临平区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330113', '钱塘区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330114', '临安区', 3, id FROM area WHERE code='330100';