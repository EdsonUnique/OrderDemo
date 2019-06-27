package edson.wechatfood.VO;

import edson.wechatfood.enums.JsonMsg;

/**
 * 返回请求结果的包装类
 */
public class ResultWrapper {

    public static ResultVO success(Object data){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(JsonMsg.SUCCESS.getCode());
        resultVO.setMsg(JsonMsg.SUCCESS.getMsg());
        resultVO.setData(data);

        return resultVO;
    }

    public static ResultVO success(){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(JsonMsg.SUCCESS.getCode());
        resultVO.setMsg(JsonMsg.SUCCESS.getMsg());
        return resultVO;
    }

    public static ResultVO error(){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(JsonMsg.FAILURE.getCode());
        resultVO.setMsg(JsonMsg.FAILURE.getMsg());
        return resultVO;
    }

}
