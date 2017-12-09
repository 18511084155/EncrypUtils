接口加密算法
 
原目标字符串：
ad=1&catid=0&channel_code=c1005&client_version=2.0.4&from=2&id=5850912&uid=32451sign=582a6c46f16312446c4888c5a9bdcea5
加密后：
nue3mra3_58=6e8wiw-uuhWCi2sOfwRHRsXMHdKmFL4AlpnuO77OXRdupg1mevaSlF_NBb1rO5V1bw9t2t4DmnzErSkDRc9NCcAW9fGaQY0S00joLpL79Zs3AqqZD52d_RavIRfu6VYEx9zgZ1p5ecv-S1wqzMXPi_-ur7DqGWlF
加密的key:mypassword01



算法的选择：

常用的加解密算法分三大类：非对称密钥加密算法、对称密钥加密算法、Hash加密算法
一、非对称密钥加密算法（RSA、DSA、ECC、DH等）：
非对称加密又叫公开密钥算法（public
 key algorithm）。这种加密算法是这样设计的：用作加密的密钥不同于用作解密的密钥，而且解密密钥不能根据加密密钥计算出来（至少在合理假定的长时间内）。之所以又叫做公开密钥算法是由于加密密钥可以公开，即陌生人可以得到它并用来加密信息，但只有用相应的解密密钥才能解密信息。在这种加密算法中，加密密钥被叫做公开密钥,而解密密钥被叫做私有密钥。非对称加密算法的加密、解密的效率比较低。在算法设计上，非对称加密算法对待加密的数据长度有着苛刻的要求。例如RSA算法要求待加密的数据不得大于53个字节。

二、对称密钥加密算法（DES、3DES、AES等）
对称钥匙加密系统是加密和解密均采用同一把秘密钥匙，而且通信双方都必须获得这把钥匙，并保持钥匙的秘密。

三、Hash加密算法（MD5、SHA等）：
散列是信息的提炼，通常其长度要比信息小得多，且为一个固定长度。加密性强的散列一定是不可逆的，这就意味着通过散列结果，无法推出任何部分的原始信息。任何输入信息的变化，哪怕仅一位，都将导致散列结果的明显变化，这称之为雪崩效应。散列还应该是防冲突的，即找不出具有相同散列结果的两条信息。具有这些特性的散列结果就可以用于验证信息是否被修改。


常用加密算法比较

http://blog.csdn.net/jiht594/article/details/6954155

AES和RSA加密算法时间效率

http://blog.csdn.net/blue1244/article/details/41790705


经过调查，对url中的参数进行加密的过程，我不建议使用rsa或者是二重以上的des这样的加密算法，主要原因在于性能和速度会受影响。
 
  
我建议大家使用对称加密如：
DES或者是PBE算法。
 
我在这边就使用DES来实现加密。
 
  
二、加密原理
 
  
对于一个纯文本，加密后它会变成一堆乱码，这堆乱码包括了许多非法字符，我们不希望
把这些字符放入
bean
中，因此在加密完后，我们还要对加密结果进行
base64
编码。
 
  
PBE
从字面上理解，它必须使用一个口令，我们不希望我们的加密过于复杂而影响页面跳
转的速度，因此我们不采用口令
+KEY
的形式，我们这边的口令就是我们的
KEY
。




过滤的规则：
http://blog.csdn.net/janronehoo/article/details/46774885
Base64.encode(salt,Base64.URL_SAFE | Base64.NO_WRAP)

使用base64 加密在URL安全传递|url_safe|php
http://blog.csdn.net/wusuopubupt/article/details/49803777
http://enenba.com/?post=282

由于目前工作安排问题：

1.可以针对部门账号信息模块和支付模板接口，进行升级适用新的验证机制。

需要改进的地方：

1.http头信息里设置公共参数

在使用http请求时常常要传递一些参数，如IMEI号、平台号、渠道号、客户端的版本号等一些通用信息，像这些参数我们没有必要每次都拼在url后，我们可以统一添加到http头里。

2.针对http请求体里面的参数，需要把所有的url参数都要进行加密，目前存在的问题是url参数过多，还有存在安全隐患。

例如我们自己的地址：

 http://www.weixinkd.com/v3/article/info/get.json?ad=1&catid=0&channel_code=c1005&client_version=2.0.4&from=2&id=5850912&uid=32451sign=582a6c46f16312446c4888c5a9bdcea5

修改以后进行加密： http://www.weixinkd.com/v3/article/info/get.json？p=nue3mra3_58=6e8wiw-uuhWCi2sOfwRHRsXMHdKmFL4AlpnuO77OXRdupg1mevaSlF_NBb1rO5V1bw9t2t4DmnzErSkDRc9NCcAW9fGaQY0S00joLpL79Zs3AqqZD52d_RavIRfu6VYEx9zgZ1p5ecv-S1wqzMXPi_-ur7DqGWlF
 
干扰字符:W
原始pubKey:05wxn0Xh0vgbIw1FNgYowyz7tTPeYwIDAQAB
过滤后pubKey:05wxn0Xh0vgbIw1FNgYowyz7tTPeY
加密串:VudRxH6vttU=hxUOnq0-r2dko0xjYLXoHkiqwenln-Ivds5RVGrMjaG8XIGpKVpVISV5hG8b0KWNMiJbREl36L2sF18t-xsVVebtYA7x_n7U6hCVK2V4LOV1cg1W1Mg8ZzGvBCj5o0iLjX5dNmqS-AA=
干扰加密以后的串:WVudRxH6vttU=hxUOnq0-r2dko0xjYLXoHkiqwenln-Ivds5RVGrMjaG8XIGpKVpVISV5hG8b0KWNMiJbREl36L2sF18t-xsVVebtYA7x_n7U6hCVK2V4LOV1cg1W1Mg8ZzGvBCj5o0iLjX5dNmqS-AA=N
----------------------------------------------------------------------------------------------------
干扰字符:n
原始pubKey:05wxn0Xh0vgbIw1FNgYowyz7tTPeYwIDAQAB
过滤后pubKey:05wxn0Xh0vgbIw1FNgYowyz7tTPeYwIDAQAB
加密串:VqiCimxhOMU=zLQDS1_pF6PqiWWECEhtcrtXwdz1fkAMY8H-vICsHDGT9kqydn7P7-CTM0ueyOg2eJGEzrN4dS8sDsgiSIILhBs6BAtu8cPUPGBInZAjwgFfcv7IzEfMReDTrISL2lGCt6mfJM6I3hw=
干扰加密以后的串:nVqiCimxhOMU=zLQDS1_pF6PqiWWECEhtcrtXwdz1fkAMY8H-vICsHDGT9kqydn7P7-CTM0ueyOg2eJGEzrN4dS8sDsgiSIILhBs6BAtu8cPUPGBInZAjwgFfcv7IzEfMReDTrISL2lGCt6mfJM6I3hw=
----------------------------------------------------------------------------------------------------
干扰字符:X
原始pubKey:05wxn0Xh0vgbIw1FNgYowyz7tTPeYwIDAQAB
过滤后pubKey:05wxn0Xh0vgbIw1FNgYowyz7tTPe
加密串:unyCIw7Wgek=WMIVru44piF-Zl0KPRTAuMp4smwB5L08IzPW3l0b5HI_XaPR6TpOw7ncng9IwmECFM8cBpRZl2xHW4thcX-WwfFfvEpcOlidBGmTe6AR0tN5HUkkQVxMPg_Ig8dmy89EkM9JqBhADxY=
干扰加密以后的串:XunyCIw7Wgek=WMIVru44piF-Zl0KPRTAuMp4smwB5L08IzPW3l0b5HI_XaPR6TpOw7ncng9IwmECFM8cBpRZl2xHW4thcX-WwfFfvEpcOlidBGmTe6AR0tN5HUkkQVxMPg_Ig8dmy89EkM9JqBhADxY=vU
 
本地客户端为了保证APK被反编译以后，对方拿不到我们的加密公钥（key）做了如下加密处理：
技术调研：
 发现在android客户端存放key到任何地方都可能被反编译以后查看到，那么我们就考虑是否有一直针对我们应用自身唯一的值，只有在运行的时候才能获取到，并且这个数据还能安全。
思考了好久和同事沟通，同事提醒了一句是否考虑下证书信息？ok，那么想到了解决方案了。
技术实现：
1.获取到证书二进制字节码信息，使用base64加密成字符串keyStr（适应url参数类型）
2. keyStr截取后面36位，私下里给服务器端同事保存在服务器作为我们的公钥pubkey.
3.考虑到key是固定的加密，加密等级有点低，使用动态干扰码来动态的截取pubkey来达到我们想要的动态key.
4.干扰码生产：
使用干扰码字典：
private static final char[] chars = {'0','1','2','3','4','5','6','7','8','9',
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
        'r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H',
        'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z’};
每次随机获取其中的一个字符char,保存在内存中。
5.动态key生产：
公钥pubkey  截取掉 (char的asccii码取它的个位数p)的后几位数，剩下的部分就是动态key了。
6.字段加密：
还是使用以前的DES加盐加密算法，key使用了上面生成的动态key（参考v5加密）
7.生成加密字段的干扰字符
上面步骤4产生的干扰字符char,放在加密字段的第一位 + 加密字段 +干扰字符（char的asccii码取它的个位数p，p除3取余数m,生成m个干扰字符），就是最终的加密字段。

服务端：
反向解析就可以了。



接口加密：
针对用户破解了，我们的接口的验签方式，导致用户恶意的注册或者刷接口，对服务器端造成严重损失。
调研：
通过调研，我们需要一种加密强度高，不易被破解。并且需要加密效率和解密速度快的一中算法，针对指定接口，进行加密处理。
通过反复研究，我们最终选择了Android端，ios端，服务器端都能通用的算法DES加盐加密算法。
客户端：
1.android首先需要针对服务器提供的key（公钥），进行混淆加密处理。这块考虑使用c代码加密一部分，android代码进行另一部分的加密，防止别人反编译我们APK破解我们的key。
2.通过代码把so和java部分的代码获取到key（公钥）,key进行MD5加密返回16位的二进制字节码，取其前8位字节码进行base64加密成兼容url参数类型的字符串saltString(盐字符串)。
3.获取到saltString(盐字符串)以后，只取其前8位字符转成二进制字节码，作为salt(盐)。
4.根据key（公钥）+salt(盐)进行DES加密，加密完成以后返回的二进制码进行base64加密成兼容url参数类型的字符串ciphertextString（为数据加密串）
5.返回给服务器的加密串需要数据干扰，saltString(盐字符串)+ciphertextString（为数据加密串）为最终返回加密串。

服务端：
1.根据客户端返回的加密串进行解密操作。
2.解密过程就省略掉。。。。(加密的逆向解密)
2.获取最终想要的结果。