# android_rn_demo
原生Android集成RN，并且实现热更新

先上图



Test为Android原生项目，用Android Studio打开即可
rn为react native 目录，如果Test和rn不是同级目录的话需要相应的修改Test根目录下build.gradle中依赖rn的相对路径

rn没有上传node_modules自己可以下载，demo中用到的插件在package.json中可以看到。创建rn项目，可以直接新建一个rn
目录然后把这个package.json拷贝进去然后调用npm i就行
