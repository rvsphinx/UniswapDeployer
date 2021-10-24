#pragma once

#define WIN32_LEAN_AND_MEAN             // 从 Windows 头文件中排除极少使用的内容
#define DLLAPI __declspec(dllexport)
// Windows 头文件
#include <windows.h>
#include "../../Common/d3dUtil.h"
// This is the base class for the class
// retrieved from the DLL. This is used simply
// so that I can show how various types should
// be retrieved from a DLL. This class is to
// show how derived classes can be taken from
// a DLL.
//struct must complete and same with application
//dll export
#ifdef __cplusplus
 extern "C" {
#endif
	typedef void(*onFramebufferCallBack)(BYTE*, int, int, int, int);//buffer w h size userid
	typedef void(*onAudioCallBack)(BYTE*, int, int);//buffer size userid

	/*
	* 服务初始化，ID用于生成不同的服务：如后续有火灾、地震游戏；
	  画面、音频回调函数指向java的处理函数入口；
	  然后创建一个C++线程进行游戏循环
	*/
	DLLAPI int d3dInitialize(int gameServiceID, onFramebufferCallBack bufferCb, onAudioCallBack audioCb);//
	
	/*
	* buffer=1280[宽]*720[高]*4Byte，每个字节代表一个颜色分量（RGBA）每个像素占4字节
	*/
	
	/*
	* MouseStatus(int 0x00 = 左右都弹起状态, 0x01 = 左键按住状态, 0x02 = 右键按住状态, 3同时按住)
	*/
	DLLAPI void setMouseMotion(int mouseStatus,int mouseX,int mouseY, _user userid);//鼠标没有移动也要传，为了每帧获取鼠标状态
	
	/*
	* MouseClickKey(int 0=没有click事件,1=左按下一瞬间,2=右按下一瞬间,3=左按弹起瞬间,4=右按弹起瞬间)
	*/
	DLLAPI void setMouseClicked(int mouseClickKey, int mouseX, int mouseY, _user userid);
	
	/*
	* keycode(int=ASCII 键盘XXX字符的按下一瞬间)
	*/
	DLLAPI void setKeyPressed(int keycode, _user userid);
	
	/*
	* 生成玩家对象，并设置云游戏画面参数、上次存档的游戏属性等
	*/
	DLLAPI void setPlayerEnter(int id, int frameWidth, int frameHeight, int kbps, int fps, void* pUserData);
	/*
	* 可配置的分辨率、画面码率kbps、画面帧数fps(未实现)
	*/
	DLLAPI void setPlayData(int id,int frameWidth, int frameHeight, int kbps ,int fps);
	
	/*
	* 玩家退出游戏后，服务器可以持久化玩家数据
	*/
	DLLAPI void getPlayerData(int id, int frameWidth, int frameHeight, int codeRate);
	
	//玩家应该还需要暂停/重传 buffer的开关，例如网络暂时中断、客户端暂时暂停

	/*
	* 删除玩家对象
	*/
	DLLAPI void setPlayerQuit(int id);
	

	DLLAPI float getTotaltime();//获取游戏总时长

	DLLAPI float getDeltatime();//获取帧间隔时间dt (fps = 1/dt)



	struct UDD {
		char* b[1280 * 720 * 4];
	};

	typedef void(*OnPlayerQuitCallBack)(float x, float y, float z);

#ifdef __cplusplus
}
#endif