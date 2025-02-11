---
title: 数据仓库ETL流程设计与实现
date: 2025-02-10
categories: 
- 技术分享
tags:
- ETL
- 数据仓库
- Python
- Airflow
---

{% note info %}
本文详细介绍数据仓库ETL流程的设计与实现，包括数据抽取、转换、加载的最佳实践。
{% endnote %}

## 架构设计

{% note primary %}
整体采用分层架构：
1. ODS层：原始数据层
2. DWD层：明细数据层
3. DWS层：服务数据层
4. ADS层：应用数据层
{% endnote %}

## ETL流程实现

### 数据抽取
```python
def extract_data(source_config):
    """
    数据抽取模块
    """
    try:
        # 建立数据源连接
        conn = create_connection(source_config)
        
        # 增量抽取逻辑
        last_etl_time = get_last_etl_time()
        sql = f"""
            SELECT * FROM source_table 
            WHERE update_time > '{last_etl_time}'
        """
        
        # 执行抽取
        df = pd.read_sql(sql, conn)
        return df
    
    except Exception as e:
        logging.error(f"数据抽取失败: {str(e)}")
        raise
```

### 数据转换
```python
def transform_data(df):
    """
    数据清洗转换
    """
    # 数据类型转换
    df['create_time'] = pd.to_datetime(df['create_time'])
    
    # 空值处理
    df['category'] = df['category'].fillna('未分类')
    
    # 业务规则转换
    df['status'] = df['status'].map({
        0: '待处理',
        1: '处理中',
        2: '已完成'
    })
    
    return df
```

### 数据加载
```python
def load_data(df, target_config):
    """
    数据加载到目标表
    """
    try:
        # 建立目标库连接
        engine = create_engine(target_config)
        
        # 分批写入
        df.to_sql(
            'target_table',
            engine,
            if_exists='append',
            index=False,
            chunksize=1000
        )
        
    except Exception as e:
        logging.error(f"数据加载失败: {str(e)}")
        raise
```

## 调度管理

使用Airflow进行任务调度：

```python
from airflow import DAG
from airflow.operators.python_operator import PythonOperator

dag = DAG(
    'etl_pipeline',
    schedule_interval='0 2 * * *',  # 每天凌晨2点执行
    start_date=datetime(2024, 1, 1)
)

extract_task = PythonOperator(
    task_id='extract_data',
    python_callable=extract_data,
    dag=dag
)

transform_task = PythonOperator(
    task_id='transform_data',
    python_callable=transform_data,
    dag=dag
)

load_task = PythonOperator(
    task_id='load_data',
    python_callable=load_data,
    dag=dag
)

extract_task >> transform_task >> load_task
```

## 监控告警

实现了完整的监控告警机制：

1. 数据质量监控
   - 空值检查
   - 重复值检查
   - 数据一致性校验

2. 任务执行监控
   - 运行状态
   - 执行时长
   - 错误日志

3. 告警通知
   - 邮件通知
   - 企业微信通知
   - 短信通知

## 性能优化

主要从以下几个方面进行了优化：

1. 分批处理
2. 并行计算
3. 索引优化
4. 资源控制

## 实践总结

1. 保证数据质量是首要任务
2. 合理的分层设计很重要
3. 监控告警要及时准确
4. 持续优化性能指标 