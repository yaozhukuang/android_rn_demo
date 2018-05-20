import {request} from './Fetch'

export default class Request {

    /**
     * 发送网络请求
     * @param url 请求地址
     * @param option 请求参数
     * @param succeed 成功回调
     * @param failed 失败回调
     */
    static send(url, option, succeed, failed) {
        request(url, option,
            (response) => {
                succeed(response);
            }, (error) => {
                if (failed) {
                    failed(error);
                } else {
                    alert(error);
                }
            });
    }

    /**
     * 发送GET网络请求
     * @param url 请求地址
     *      * @param option 请求参数
     * @param succeed 成功回调
     * @param failed 失败回调
     */
    static get(url, option = null, succeed, failed) {
        this.send(url, {...option, method: 'GET'}, succeed, failed);
    }

    /**
     * 发送POST网络请求
     * @param url 请求地址
     * @param option 请求参数
     * @param succeed 成功回调
     * @param failed 失败回调
     */
    static post(url, option = null, succeed, failed) {
        this.send(url, {
            ...option,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        }, succeed, failed);
    }
}