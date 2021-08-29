import React, {useState, useEffect, useReducer} from 'react';
import { Switch,Table, Tag, Row, Col, Spin, Space} from 'antd';
import { LoadingOutlined } from '@ant-design/icons';
import {Line} from '@ant-design/charts';
import moment from 'moment';

import fetcher from '../actions/fetch';
import motionsReducer from '../reducers/motions';
import statsReducer from '../reducers/stats';


const HomePage = () => {

    const [motions, dispatchMotions] = useReducer(motionsReducer,[]);
    const [stats, dispatchStats] = useReducer(statsReducer,[]);
    const [loadingTable,setLoadingTable] = useState(true); 
    const [loadingPIRSwitch,setLoadingPIRSwitch] = useState(false);
    const [loadingAlarmSwitch,setLoadingAlarmSwitch] = useState(false);
    const [isDisabled, setIsDisabled] = useState(false);
    const [pirState, setPirState] = useState(false);
    const [alarmState,setAlarmState] = useState(false);
    const [loadingPage,setLoadingPage] = useState(true);
    

    var config = {
        data: stats,
        xField: 'day',
        yField: 'value',
        label: {},
        point: {
          size: 5,
          shape: 'diamond',
          style: {
            fill: 'white',
            stroke: '#5B8FF9',
            lineWidth: 2,
          },
        },
        tooltip: { showMarkers: false },
        state: {
          active: {
            style: {
              shadowBlur: 4,
              stroke: '#000',
              fill: 'red',
            },
          },
        },
        interactions: [{ type: 'marker-active' }],
      };

    const getMotions = () => {
        let motions=[];
        fetcher("/motions",{method:'GET'})
        .then(response => {
            if(response !== null){
                response.map((motion)=>{
                    motions.push({id:motion.id,timestamp:moment(motion.timestamp).format("DD.MM.YYYY HH:mm:ss")});
                });
                dispatchMotions({type:'POPULATE_MOTIONS',motions});
                getStatistics(motions);
            }
        });
        
    }

    const togglePIRSwitch = () => {
        setLoadingPIRSwitch(true);
        setLoadingAlarmSwitch(true);
        fetcher("/toggleSensor",{method:'POST',body:!pirState})
        .then(response => {
            if(response !== null){
                setPirState(response.pirSensor);
                setAlarmState(response.alarm);
            }
            setLoadingAlarmSwitch(false);
            setLoadingPIRSwitch(false);
        });
    }

    const toggleAlarmSwitch = () => {
        setLoadingAlarmSwitch(true);
        fetcher("/toggleAlarm",{method:'POST',body:!alarmState})
        .then(response => {
            if(response !== null){
                setAlarmState(response.alarm);
            }
            setLoadingAlarmSwitch(false);
        });
    
    }

    const getInitialSensorValues = () => {
        setLoadingAlarmSwitch(true);
        setLoadingPIRSwitch(true);
        fetcher("/checkSensorState",{method:'GET'})
        .then(response => {
            if(response !== null){
                setAlarmState(response.alarm);
                setPirState(response.pirSensor);
            }
            setLoadingPIRSwitch(false);
            setLoadingAlarmSwitch(false);
        })
    }

    const getStatistics = (motions) =>{
        let statistics = [];
        let days = [];
        motions.forEach(motion => {
            let day = moment(motion.timestamp,"DD.MM.YYYY HH:mm:ss").format("DD.MM.YYYY");
            if(days === [] || !days.includes(day) ){
                days.push(day);
            }
        });
        days.forEach(day => {
            statistics.push({day,value:(motions.filter(motion => moment(motion.timestamp,"DD.MM.YYYY HH:mm:ss").format("DD.MM.YYYY") === day)).length});
        });
        dispatchStats({type: 'GET_STATS',statistics});
    }

    useEffect(()=>{
        getInitialSensorValues();
        getMotions();
        const interval = setInterval(()=> {
            getMotions(); 
        },10000);
        setLoadingPage(false);
        return () => clearInterval(interval);
       
     },[]);

    useEffect(()=>{
        if(!pirState){
            setIsDisabled(true);
            setAlarmState(false);
            setLoadingTable(false);
        }else{
            setLoadingTable(true);
            setIsDisabled(false);
        }
    },[pirState]);

    const columns = [
        {
          title: 'ID',
          dataIndex: 'id',
          key: 'id',
          sorter: (a,b) => a.id-b.id,
        },
        {
          title: 'Timestamp',
          dataIndex: 'timestamp',
          key: 'timestamp',
          defaultSortOrder:'descend',
          sortDirections:['descend','ascend','descend'],
          sorter: (a,b) => moment(a.timestamp,"DD.MM.YYYY HH:mm:ss").unix() - moment(b.timestamp,"DD.MM.YYYY HH:mm:ss").unix(),
        },
        {
            title: <LoadingOutlined style={{ fontSize: 24, }} spin={loadingTable} />,
        }
      ];



    return(
        <div >
            {loadingPage ? <Spin size="large"/> : 
            <div className="content">
                <div className="controlls">
                    <Space size={50}>
                        <span>
                            <Tag color="magenta">PIR sensor State</Tag>
                            <Switch onClick={togglePIRSwitch} checked={pirState} loading={loadingPIRSwitch} checkedChildren="ON" unCheckedChildren="OFF"/>
                        </span>
                        <span>
                            <Tag color="magenta">Alarm</Tag>
                            <Switch onClick={toggleAlarmSwitch} checked={alarmState} disabled={isDisabled} loading={loadingAlarmSwitch} checkedChildren="ON" unCheckedChildren="OFF"/>
                        </span>
                    </Space>
                </div>
                <br/>
                
                    <Row>
                    <Col span="9">
                    <Table dataSource={motions} style={{width:"27vw"}} pagination={{pageSize:5, showSizeChanger:false}} columns={columns}/>
                    </Col>
                    {/* <Spin size="small" indicator={loader} spinning={loadingTable}/>*/}
                    <Col span="15">
                    <Line {...config}/>
                    </Col>
                    </Row>
              
            </div>}
  </div>
    );
}

export { HomePage as default }