import BaseComponent from './BaseComponent'
import {connect} from "react-redux";

class ReduxComponent extends BaseComponent {

}

function mapStateToProps(state) {
    return {
        state: state
    }
}

export default connect(mapStateToProps)(ReduxComponent)