-- 完整全国省市区数据

-- 清空现有数据
DELETE FROM area;

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
-- 北京市
INSERT INTO area (code, name, level, parent_id) SELECT '110100', '北京市', 2, id FROM area WHERE code='110000';
-- 天津市
INSERT INTO area (code, name, level, parent_id) SELECT '120100', '天津市', 2, id FROM area WHERE code='120000';
-- 河北省
INSERT INTO area (code, name, level, parent_id) SELECT '130100', '石家庄市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '130200', '唐山市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '130300', '秦皇岛市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '130400', '邯郸市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '130500', '邢台市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '130600', '保定市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '130700', '张家口市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '130800', '承德市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '130900', '沧州市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '131000', '廊坊市', 2, id FROM area WHERE code='130000';
INSERT INTO area (code, name, level, parent_id) SELECT '131100', '衡水市', 2, id FROM area WHERE code='130000';
-- 山西省
INSERT INTO area (code, name, level, parent_id) SELECT '140100', '太原市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '140200', '大同市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '140300', '阳泉市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '140400', '长治市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '140500', '晋城市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '140600', '朔州市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '140700', '晋中市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '140800', '运城市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '140900', '忻州市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '141000', '临汾市', 2, id FROM area WHERE code='140000';
INSERT INTO area (code, name, level, parent_id) SELECT '141100', '吕梁市', 2, id FROM area WHERE code='140000';
-- 内蒙古
INSERT INTO area (code, name, level, parent_id) SELECT '150100', '呼和浩特市', 2, id FROM area WHERE code='150000';
INSERT INTO area (code, name, level, parent_id) SELECT '150200', '包头市', 2, id FROM area WHERE code='150000';
INSERT INTO area (code, name, level, parent_id) SELECT '150300', '乌海市', 2, id FROM area WHERE code='150000';
INSERT INTO area (code, name, level, parent_id) SELECT '150400', '赤峰市', 2, id FROM area WHERE code='150000';
INSERT INTO area (code, name, level, parent_id) SELECT '150500', '通辽市', 2, id FROM area WHERE code='150000';
INSERT INTO area (code, name, level, parent_id) SELECT '150600', '鄂尔多斯市', 2, id FROM area WHERE code='150000';
INSERT INTO area (code, name, level, parent_id) SELECT '150700', '呼伦贝尔市', 2, id FROM area WHERE code='150000';
INSERT INTO area (code, name, level, parent_id) SELECT '150800', '巴彦淖尔市', 2, id FROM area WHERE code='150000';
INSERT INTO area (code, name, level, parent_id) SELECT '150900', '乌兰察布市', 2, id FROM area WHERE code='150000';
-- 辽宁省
INSERT INTO area (code, name, level, parent_id) SELECT '210100', '沈阳市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '210200', '大连市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '210300', '鞍山市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '210400', '抚顺市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '210500', '本溪市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '210600', '丹东市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '210700', '锦州市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '210800', '营口市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '210900', '阜新市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '211000', '辽阳市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '211100', '盘锦市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '211200', '铁岭市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '211300', '朝阳市', 2, id FROM area WHERE code='210000';
INSERT INTO area (code, name, level, parent_id) SELECT '211400', '葫芦岛市', 2, id FROM area WHERE code='210000';
-- 吉林省
INSERT INTO area (code, name, level, parent_id) SELECT '220100', '长春市', 2, id FROM area WHERE code='220000';
INSERT INTO area (code, name, level, parent_id) SELECT '220200', '吉林市', 2, id FROM area WHERE code='220000';
INSERT INTO area (code, name, level, parent_id) SELECT '220300', '四平市', 2, id FROM area WHERE code='220000';
INSERT INTO area (code, name, level, parent_id) SELECT '220400', '辽源市', 2, id FROM area WHERE code='220000';
INSERT INTO area (code, name, level, parent_id) SELECT '220500', '通化市', 2, id FROM area WHERE code='220000';
INSERT INTO area (code, name, level, parent_id) SELECT '220600', '白山市', 2, id FROM area WHERE code='220000';
INSERT INTO area (code, name, level, parent_id) SELECT '220700', '松原市', 2, id FROM area WHERE code='220000';
INSERT INTO area (code, name, level, parent_id) SELECT '220800', '白城市', 2, id FROM area WHERE code='220000';
-- 黑龙江省
INSERT INTO area (code, name, level, parent_id) SELECT '230100', '哈尔滨市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '230200', '齐齐哈尔市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '230300', '鸡西市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '230400', '鹤岗市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '230500', '双鸭山市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '230600', '大庆市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '230700', '伊春市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '230800', '佳木斯市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '230900', '七台河市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '231000', '牡丹江市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '231100', '黑河市', 2, id FROM area WHERE code='230000';
INSERT INTO area (code, name, level, parent_id) SELECT '231200', '绥化市', 2, id FROM area WHERE code='230000';
-- 上海市
INSERT INTO area (code, name, level, parent_id) SELECT '310100', '上海市', 2, id FROM area WHERE code='310000';
-- 江苏省
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
-- 浙江省
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
-- 安徽省
INSERT INTO area (code, name, level, parent_id) SELECT '340100', '合肥市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '340200', '芜湖市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '340300', '蚌埠市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '340400', '淮南市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '340500', '马鞍山市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '340600', '淮北市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '340700', '铜陵市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '340800', '安庆市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '341000', '黄山市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '341100', '滁州市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '341200', '阜阳市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '341300', '宿州市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '341500', '六安市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '341600', '亳州市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '341700', '池州市', 2, id FROM area WHERE code='340000';
INSERT INTO area (code, name, level, parent_id) SELECT '341800', '宣城市', 2, id FROM area WHERE code='340000';
-- 福建省
INSERT INTO area (code, name, level, parent_id) SELECT '350100', '福州市', 2, id FROM area WHERE code='350000';
INSERT INTO area (code, name, level, parent_id) SELECT '350200', '厦门市', 2, id FROM area WHERE code='350000';
INSERT INTO area (code, name, level, parent_id) SELECT '350300', '莆田市', 2, id FROM area WHERE code='350000';
INSERT INTO area (code, name, level, parent_id) SELECT '350400', '三明市', 2, id FROM area WHERE code='350000';
INSERT INTO area (code, name, level, parent_id) SELECT '350500', '泉州市', 2, id FROM area WHERE code='350000';
INSERT INTO area (code, name, level, parent_id) SELECT '350600', '漳州市', 2, id FROM area WHERE code='350000';
INSERT INTO area (code, name, level, parent_id) SELECT '350700', '南平市', 2, id FROM area WHERE code='350000';
INSERT INTO area (code, name, level, parent_id) SELECT '350800', '龙岩市', 2, id FROM area WHERE code='350000';
INSERT INTO area (code, name, level, parent_id) SELECT '350900', '宁德市', 2, id FROM area WHERE code='350000';
-- 江西省
INSERT INTO area (code, name, level, parent_id) SELECT '360100', '南昌市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '360200', '景德镇市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '360300', '萍乡市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '360400', '九江市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '360500', '新余市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '360600', '鹰潭市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '360700', '赣州市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '360800', '吉安市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '360900', '宜春市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '361000', '抚州市', 2, id FROM area WHERE code='360000';
INSERT INTO area (code, name, level, parent_id) SELECT '361100', '上饶市', 2, id FROM area WHERE code='360000';
-- 山东省
INSERT INTO area (code, name, level, parent_id) SELECT '370100', '济南市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '370200', '青岛市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '370300', '淄博市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '370400', '枣庄市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '370500', '东营市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '370600', '烟台市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '370700', '潍坊市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '370800', '济宁市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '370900', '泰安市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '371000', '威海市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '371100', '日照市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '371300', '临沂市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '371400', '德州市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '371500', '聊城市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '371600', '滨州市', 2, id FROM area WHERE code='370000';
INSERT INTO area (code, name, level, parent_id) SELECT '371700', '菏泽市', 2, id FROM area WHERE code='370000';
-- 河南省
INSERT INTO area (code, name, level, parent_id) SELECT '410100', '郑州市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '410200', '开封市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '410300', '洛阳市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '410400', '平顶山市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '410500', '安阳市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '410600', '鹤壁市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '410700', '新乡市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '410800', '焦作市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '410900', '濮阳市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '411000', '许昌市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '411100', '漯河市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '411200', '三门峡市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '411300', '南阳市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '411400', '商丘市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '411500', '信阳市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '411600', '周口市', 2, id FROM area WHERE code='410000';
INSERT INTO area (code, name, level, parent_id) SELECT '411700', '驻马店市', 2, id FROM area WHERE code='410000';
-- 湖北省
INSERT INTO area (code, name, level, parent_id) SELECT '420100', '武汉市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '420200', '黄石市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '420300', '十堰市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '420500', '宜昌市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '420600', '襄阳市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '420700', '鄂州市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '420800', '荆门市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '420900', '孝感市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '421000', '荆州市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '421100', '黄冈市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '421200', '咸宁市', 2, id FROM area WHERE code='420000';
INSERT INTO area (code, name, level, parent_id) SELECT '421300', '随州市', 2, id FROM area WHERE code='420000';
-- 湖南省
INSERT INTO area (code, name, level, parent_id) SELECT '430100', '长沙市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '430200', '株洲市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '430300', '湘潭市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '430400', '衡阳市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '430500', '邵阳市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '430600', '岳阳市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '430700', '常德市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '430800', '张家界市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '430900', '益阳市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '431000', '郴州市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '431100', '永州市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '431200', '怀化市', 2, id FROM area WHERE code='430000';
INSERT INTO area (code, name, level, parent_id) SELECT '431300', '娄底市', 2, id FROM area WHERE code='430000';
-- 广东省
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
-- 广西
INSERT INTO area (code, name, level, parent_id) SELECT '450100', '南宁市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '450200', '柳州市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '450300', '桂林市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '450400', '梧州市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '450500', '北海市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '450600', '防城港市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '450700', '钦州市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '450800', '贵港市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '450900', '玉林市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '451000', '百色市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '451100', '贺州市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '451200', '河池市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '451300', '来宾市', 2, id FROM area WHERE code='450000';
INSERT INTO area (code, name, level, parent_id) SELECT '451400', '崇左市', 2, id FROM area WHERE code='450000';
-- 海南省
INSERT INTO area (code, name, level, parent_id) SELECT '460100', '海口市', 2, id FROM area WHERE code='460000';
INSERT INTO area (code, name, level, parent_id) SELECT '460200', '三亚市', 2, id FROM area WHERE code='460000';
INSERT INTO area (code, name, level, parent_id) SELECT '460300', '三沙市', 2, id FROM area WHERE code='460000';
INSERT INTO area (code, name, level, parent_id) SELECT '460400', '儋州市', 2, id FROM area WHERE code='460000';
-- 重庆市
INSERT INTO area (code, name, level, parent_id) SELECT '500100', '重庆市', 2, id FROM area WHERE code='500000';
-- 四川省
INSERT INTO area (code, name, level, parent_id) SELECT '510100', '成都市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '510300', '自贡市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '510400', '攀枝花市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '510500', '泸州市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '510600', '德阳市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '510700', '绵阳市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '510800', '广元市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '510900', '遂宁市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511000', '内江市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511100', '乐山市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511300', '南充市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511400', '眉山市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511500', '宜宾市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511600', '广安市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511700', '达州市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511800', '雅安市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '511900', '巴中市', 2, id FROM area WHERE code='510000';
INSERT INTO area (code, name, level, parent_id) SELECT '512000', '资阳市', 2, id FROM area WHERE code='510000';
-- 贵州省
INSERT INTO area (code, name, level, parent_id) SELECT '520100', '贵阳市', 2, id FROM area WHERE code='520000';
INSERT INTO area (code, name, level, parent_id) SELECT '520200', '六盘水市', 2, id FROM area WHERE code='520000';
INSERT INTO area (code, name, level, parent_id) SELECT '520300', '遵义市', 2, id FROM area WHERE code='520000';
INSERT INTO area (code, name, level, parent_id) SELECT '520400', '安顺市', 2, id FROM area WHERE code='520000';
INSERT INTO area (code, name, level, parent_id) SELECT '520500', '毕节市', 2, id FROM area WHERE code='520000';
INSERT INTO area (code, name, level, parent_id) SELECT '520600', '铜仁市', 2, id FROM area WHERE code='520000';
-- 云南省
INSERT INTO area (code, name, level, parent_id) SELECT '530100', '昆明市', 2, id FROM area WHERE code='530000';
INSERT INTO area (code, name, level, parent_id) SELECT '530300', '曲靖市', 2, id FROM area WHERE code='530000';
INSERT INTO area (code, name, level, parent_id) SELECT '530400', '玉溪市', 2, id FROM area WHERE code='530000';
INSERT INTO area (code, name, level, parent_id) SELECT '530500', '保山市', 2, id FROM area WHERE code='530000';
INSERT INTO area (code, name, level, parent_id) SELECT '530600', '昭通市', 2, id FROM area WHERE code='530000';
INSERT INTO area (code, name, level, parent_id) SELECT '530700', '丽江市', 2, id FROM area WHERE code='530000';
INSERT INTO area (code, name, level, parent_id) SELECT '530800', '普洱市', 2, id FROM area WHERE code='530000';
INSERT INTO area (code, name, level, parent_id) SELECT '530900', '临沧市', 2, id FROM area WHERE code='530000';
-- 西藏
INSERT INTO area (code, name, level, parent_id) SELECT '540100', '拉萨市', 2, id FROM area WHERE code='540000';
INSERT INTO area (code, name, level, parent_id) SELECT '540200', '日喀则市', 2, id FROM area WHERE code='540000';
INSERT INTO area (code, name, level, parent_id) SELECT '540300', '昌都市', 2, id FROM area WHERE code='540000';
INSERT INTO area (code, name, level, parent_id) SELECT '540400', '林芝市', 2, id FROM area WHERE code='540000';
INSERT INTO area (code, name, level, parent_id) SELECT '540500', '山南市', 2, id FROM area WHERE code='540000';
INSERT INTO area (code, name, level, parent_id) SELECT '540600', '那曲市', 2, id FROM area WHERE code='540000';
-- 陕西省
INSERT INTO area (code, name, level, parent_id) SELECT '610100', '西安市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '610200', '铜川市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '610300', '宝鸡市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '610400', '咸阳市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '610500', '渭南市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '610600', '延安市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '610700', '汉中市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '610800', '榆林市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '610900', '安康市', 2, id FROM area WHERE code='610000';
INSERT INTO area (code, name, level, parent_id) SELECT '611000', '商洛市', 2, id FROM area WHERE code='610000';
-- 甘肃省
INSERT INTO area (code, name, level, parent_id) SELECT '620100', '兰州市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '620200', '嘉峪关市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '620300', '金昌市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '620400', '白银市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '620500', '天水市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '620600', '武威市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '620700', '张掖市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '620800', '平凉市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '620900', '酒泉市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '621000', '庆阳市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '621100', '定西市', 2, id FROM area WHERE code='620000';
INSERT INTO area (code, name, level, parent_id) SELECT '621200', '陇南市', 2, id FROM area WHERE code='620000';
-- 青海省
INSERT INTO area (code, name, level, parent_id) SELECT '630100', '西宁市', 2, id FROM area WHERE code='630000';
INSERT INTO area (code, name, level, parent_id) SELECT '630200', '海东市', 2, id FROM area WHERE code='630000';
-- 宁夏
INSERT INTO area (code, name, level, parent_id) SELECT '640100', '银川市', 2, id FROM area WHERE code='640000';
INSERT INTO area (code, name, level, parent_id) SELECT '640200', '石嘴山市', 2, id FROM area WHERE code='640000';
INSERT INTO area (code, name, level, parent_id) SELECT '640300', '吴忠市', 2, id FROM area WHERE code='640000';
INSERT INTO area (code, name, level, parent_id) SELECT '640400', '固原市', 2, id FROM area WHERE code='640000';
INSERT INTO area (code, name, level, parent_id) SELECT '640500', '中卫市', 2, id FROM area WHERE code='640000';
-- 新疆
INSERT INTO area (code, name, level, parent_id) SELECT '650100', '乌鲁木齐市', 2, id FROM area WHERE code='650000';
INSERT INTO area (code, name, level, parent_id) SELECT '650200', '克拉玛依市', 2, id FROM area WHERE code='650000';
INSERT INTO area (code, name, level, parent_id) SELECT '650400', '吐鲁番市', 2, id FROM area WHERE code='650000';
INSERT INTO area (code, name, level, parent_id) SELECT '650500', '哈密市', 2, id FROM area WHERE code='650000';

-- ==================== 区级数据（主要城市） ====================
-- 北京市
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

-- 广州市
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

-- 深圳市
INSERT INTO area (code, name, level, parent_id) SELECT '440303', '罗湖区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440304', '福田区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440305', '南山区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440306', '宝安区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440307', '龙岗区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440308', '盐田区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440309', '龙华区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440310', '坪山区', 3, id FROM area WHERE code='440300';
INSERT INTO area (code, name, level, parent_id) SELECT '440311', '光明区', 3, id FROM area WHERE code='440300';

-- 成都市
INSERT INTO area (code, name, level, parent_id) SELECT '510104', '锦江区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510105', '青羊区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510106', '金牛区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510107', '武侯区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510108', '成华区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510112', '龙泉驿区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510113', '青白江区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510114', '新都区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510115', '温江区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510116', '双流区', 3, id FROM area WHERE code='510100';
INSERT INTO area (code, name, level, parent_id) SELECT '510117', '郫都区', 3, id FROM area WHERE code='510100';

-- 南京市
INSERT INTO area (code, name, level, parent_id) SELECT '320102', '玄武区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320104', '秦淮区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320105', '建邺区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320106', '鼓楼区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320111', '浦口区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320113', '栖霞区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320114', '雨花台区', 3, id FROM area WHERE code='320100';
INSERT INTO area (code, name, level, parent_id) SELECT '320115', '江宁区', 3, id FROM area WHERE code='320100';

-- 杭州市
INSERT INTO area (code, name, level, parent_id) SELECT '330102', '上城区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330105', '拱墅区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330106', '西湖区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330108', '滨江区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330109', '萧山区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330110', '余杭区', 3, id FROM area WHERE code='330100';
INSERT INTO area (code, name, level, parent_id) SELECT '330111', '富阳区', 3, id FROM area WHERE code='330100';

-- 武汉市
INSERT INTO area (code, name, level, parent_id) SELECT '420102', '江岸区', 3, id FROM area WHERE code='420100';
INSERT INTO area (code, name, level, parent_id) SELECT '420103', '江汉区', 3, id FROM area WHERE code='420100';
INSERT INTO area (code, name, level, parent_id) SELECT '420104', '硚口区', 3, id FROM area WHERE code='420100';
INSERT INTO area (code, name, level, parent_id) SELECT '420105', '汉阳区', 3, id FROM area WHERE code='420100';
INSERT INTO area (code, name, level, parent_id) SELECT '420106', '武昌区', 3, id FROM area WHERE code='420100';
INSERT INTO area (code, name, level, parent_id) SELECT '420107', '青山区', 3, id FROM area WHERE code='420100';
INSERT INTO area (code, name, level, parent_id) SELECT '420111', '洪山区', 3, id FROM area WHERE code='420100';

-- 长沙市
INSERT INTO area (code, name, level, parent_id) SELECT '430102', '芙蓉区', 3, id FROM area WHERE code='430100';
INSERT INTO area (code, name, level, parent_id) SELECT '430103', '天心区', 3, id FROM area WHERE code='430100';
INSERT INTO area (code, name, level, parent_id) SELECT '430104', '岳麓区', 3, id FROM area WHERE code='430100';
INSERT INTO area (code, name, level, parent_id) SELECT '430105', '开福区', 3, id FROM area WHERE code='430100';
INSERT INTO area (code, name, level, parent_id) SELECT '430111', '雨花区', 3, id FROM area WHERE code='430100';

-- 西安市
INSERT INTO area (code, name, level, parent_id) SELECT '610102', '新城区', 3, id FROM area WHERE code='610100';
INSERT INTO area (code, name, level, parent_id) SELECT '610103', '碑林区', 3, id FROM area WHERE code='610100';
INSERT INTO area (code, name, level, parent_id) SELECT '610104', '莲湖区', 3, id FROM area WHERE code='610100';
INSERT INTO area (code, name, level, parent_id) SELECT '610111', '灞桥区', 3, id FROM area WHERE code='610100';
INSERT INTO area (code, name, level, parent_id) SELECT '610112', '未央区', 3, id FROM area WHERE code='610100';
INSERT INTO area (code, name, level, parent_id) SELECT '610113', '雁塔区', 3, id FROM area WHERE code='610100';

-- 厦门市
INSERT INTO area (code, name, level, parent_id) SELECT '350203', '思明区', 3, id FROM area WHERE code='350200';
INSERT INTO area (code, name, level, parent_id) SELECT '350205', '海沧区', 3, id FROM area WHERE code='350200';
INSERT INTO area (code, name, level, parent_id) SELECT '350206', '湖里区', 3, id FROM area WHERE code='350200';
INSERT INTO area (code, name, level, parent_id) SELECT '350211', '集美区', 3, id FROM area WHERE code='350200';
INSERT INTO area (code, name, level, parent_id) SELECT '350212', '同安区', 3, id FROM area WHERE code='350200';
INSERT INTO area (code, name, level, parent_id) SELECT '350213', '翔安区', 3, id FROM area WHERE code='350200';

-- 郑州市
INSERT INTO area (code, name, level, parent_id) SELECT '410102', '中原区', 3, id FROM area WHERE code='410100';
INSERT INTO area (code, name, level, parent_id) SELECT '410103', '二七区', 3, id FROM area WHERE code='410100';
INSERT INTO area (code, name, level, parent_id) SELECT '410104', '管城回族区', 3, id FROM area WHERE code='410100';
INSERT INTO area (code, name, level, parent_id) SELECT '410105', '金水区', 3, id FROM area WHERE code='410100';

-- 济南市
INSERT INTO area (code, name, level, parent_id) SELECT '370102', '历下区', 3, id FROM area WHERE code='370100';
INSERT INTO area (code, name, level, parent_id) SELECT '370103', '市中区', 3, id FROM area WHERE code='370100';
INSERT INTO area (code, name, level, parent_id) SELECT '370104', '槐荫区', 3, id FROM area WHERE code='370100';
INSERT INTO area (code, name, level, parent_id) SELECT '370105', '天桥区', 3, id FROM area WHERE code='370100';
INSERT INTO area (code, name, level, parent_id) SELECT '370112', '历城区', 3, id FROM area WHERE code='370100';

-- 合肥市
INSERT INTO area (code, name, level, parent_id) SELECT '340102', '瑶海区', 3, id FROM area WHERE code='340100';
INSERT INTO area (code, name, level, parent_id) SELECT '340103', '庐阳区', 3, id FROM area WHERE code='340100';
INSERT INTO area (code, name, level, parent_id) SELECT '340104', '蜀山区', 3, id FROM area WHERE code='340100';
INSERT INTO area (code, name, level, parent_id) SELECT '340111', '包河区', 3, id FROM area WHERE code='340100';

-- 青岛市
INSERT INTO area (code, name, level, parent_id) SELECT '370202', '市南区', 3, id FROM area WHERE code='370200';
INSERT INTO area (code, name, level, parent_id) SELECT '370203', '市北区', 3, id FROM area WHERE code='370200';
INSERT INTO area (code, name, level, parent_id) SELECT '370211', '黄岛区', 3, id FROM area WHERE code='370200';
INSERT INTO area (code, name, level, parent_id) SELECT '370212', '崂山区', 3, id FROM area WHERE code='370200';
INSERT INTO area (code, name, level, parent_id) SELECT '370213', '李沧区', 3, id FROM area WHERE code='370200';

-- 大连市
INSERT INTO area (code, name, level, parent_id) SELECT '210202', '中山区', 3, id FROM area WHERE code='210200';
INSERT INTO area (code, name, level, parent_id) SELECT '210203', '西岗区', 3, id FROM area WHERE code='210200';
INSERT INTO area (code, name, level, parent_id) SELECT '210204', '沙河口区', 3, id FROM area WHERE code='210200';
INSERT INTO area (code, name, level, parent_id) SELECT '210211', '甘井子区', 3, id FROM area WHERE code='210200';

-- 沈阳市
INSERT INTO area (code, name, level, parent_id) SELECT '210102', '和平区', 3, id FROM area WHERE code='210100';
INSERT INTO area (code, name, level, parent_id) SELECT '210103', '沈河区', 3, id FROM area WHERE code='210100';
INSERT INTO area (code, name, level, parent_id) SELECT '210104', '大东区', 3, id FROM area WHERE code='210100';
INSERT INTO area (code, name, level, parent_id) SELECT '210105', '皇姑区', 3, id FROM area WHERE code='210100';
INSERT INTO area (code, name, level, parent_id) SELECT '210106', '铁西区', 3, id FROM area WHERE code='210100';

-- 哈尔滨市
INSERT INTO area (code, name, level, parent_id) SELECT '230102', '道里区', 3, id FROM area WHERE code='230100';
INSERT INTO area (code, name, level, parent_id) SELECT '230103', '南岗区', 3, id FROM area WHERE code='230100';
INSERT INTO area (code, name, level, parent_id) SELECT '230104', '道外区', 3, id FROM area WHERE code='230100';
INSERT INTO area (code, name, level, parent_id) SELECT '230108', '平房区', 3, id FROM area WHERE code='230100';
INSERT INTO area (code, name, level, parent_id) SELECT '230109', '松北区', 3, id FROM area WHERE code='230100';

-- 重庆市补充
INSERT INTO area (code, name, level, parent_id) SELECT '500114', '黔江区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500115', '长寿区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500116', '江津区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500117', '合川区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500118', '永川区', 3, id FROM area WHERE code='500100';
INSERT INTO area (code, name, level, parent_id) SELECT '500119', '南川区', 3, id FROM area WHERE code='500100';

-- 香港特别行政区
INSERT INTO area (code, name, level, parent_id) SELECT '810100', '香港岛', 2, id FROM area WHERE code='810000';
INSERT INTO area (code, name, level, parent_id) SELECT '810200', '九龙', 2, id FROM area WHERE code='810000';
INSERT INTO area (code, name, level, parent_id) SELECT '810300', '新界', 2, id FROM area WHERE code='810000';

-- 澳门特别行政区
INSERT INTO area (code, name, level, parent_id) SELECT '820100', '澳门半岛', 2, id FROM area WHERE code='820000';
INSERT INTO area (code, name, level, parent_id) SELECT '820200', '氹仔', 2, id FROM area WHERE code='820000';
INSERT INTO area (code, name, level, parent_id) SELECT '820300', '路环', 2, id FROM area WHERE code='820000';

-- 台湾省
INSERT INTO area (code, name, level, parent_id) SELECT '710100', '台北市', 2, id FROM area WHERE code='710000';
INSERT INTO area (code, name, level, parent_id) SELECT '710200', '高雄市', 2, id FROM area WHERE code='710000';
INSERT INTO area (code, name, level, parent_id) SELECT '710300', '台南市', 2, id FROM area WHERE code='710000';
INSERT INTO area (code, name, level, parent_id) SELECT '710400', '新北市', 2, id FROM area WHERE code='710000';
INSERT INTO area (code, name, level, parent_id) SELECT '710500', '桃园市', 2, id FROM area WHERE code='710000';
INSERT INTO area (code, name, level, parent_id) SELECT '710600', '台中市', 2, id FROM area WHERE code='710000';