//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    recorderManager: null,
    overFlag: true,   //设置一个按钮flag,在后台处理期间无法点击
    asrRes: ""
  },
  //长按录音事件  
  longpress(){
    var recorderManager = wx.getRecorderManager()
    this.setData({
      recorderManager: recorderManager
    })
    console.log("长按开始,开始录音");
    //百度API支持:原始 PCM 的录音参数必须符合 8k/16k 采样率、16bit 位深、单声道，支持的格式有：pcm（不压缩）、wav（不压缩，pcm编码）、amr（压缩格式）。 60S 长度  
    recorderManager.start({
      numberOfChannels: 1,   //录音通道数
      format: 'mp3',
      sampleRate: 16000,
      encodeBitRate: 64000
    })
  },
  //离开按钮
  leave(){
    console.log("结束录音");
    var that = this ; 
    this.data.recorderManager.stop()
    this.data.recorderManager.onStop(function(e){
      console.log("输出录音信息:"+e)
      
      //将临时文件路径发送给后台进行处理
      wx.uploadFile({
        url: app.globalData.asrip + 'getRecoding',
        filePath: e.tempFilePath,
        name: 'file',  //通过name后端获取文件的二进制内容
        success: res=>{
          console.log(res)
          if (res.data =="asr fail"){
            console.log("弹出翻译失败!")
          }else{
            that.setData({
              asrRes: res.data
            })
          }
          
        },
        fail: err=>{
          console.log(err)
        }
      })
    })
  },

  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }
})
