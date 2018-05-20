import {StatusBar} from 'react-native';
import {higher} from '../utils/Phone'

export const dimens = {
    statusBarHeight: statusBarHeight = higher(19) ? StatusBar.currentHeight : 0,
    navigatorHeight:44
};