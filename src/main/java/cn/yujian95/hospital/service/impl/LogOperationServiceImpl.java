package cn.yujian95.hospital.service.impl;

import cn.yujian95.hospital.dto.WebLogDTO;
import cn.yujian95.hospital.entity.LogOperation;
import cn.yujian95.hospital.entity.LogOperationExample;
import cn.yujian95.hospital.mapper.LogOperationMapper;
import cn.yujian95.hospital.service.ILogOperationService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author YuJian95  clj9509@163.com
 * @date 2020/1/20
 */

@Service
public class LogOperationServiceImpl implements ILogOperationService {

    @Resource
    private LogOperationMapper operationMapper;

    /**
     * 创建操作记录
     *
     * @param dto 记录参数
     * @return 是否成功
     */
    @Override
    public boolean insert(WebLogDTO dto) {
        LogOperation record = new LogOperation();

        BeanUtils.copyProperties(dto, record);

        record.setResult(dto.getResult().toString());

        if (dto.getParameter() != null) {
            record.setParameter(dto.getParameter().toString());
        }

        Date date = new Date();

        record.setGmtCreate(date);
        record.setGmtModified(date);

        return operationMapper.insertSelective(record) > 0;
    }

    /**
     * 查找操作记录
     *
     * @param accountName 用户名称
     * @param method      请求方法
     * @param pageNum     第几页
     * @param pageSize    页大小
     * @return 操作记录表
     */
    @Override
    public List<LogOperation> search(String accountName, String method, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        LogOperationExample example = new LogOperationExample();

        LogOperationExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(accountName)) {
            criteria.andAccountNameEqualTo(accountName);
        }

        if (!StringUtils.isEmpty(method)) {
            criteria.andMethodEqualTo(method);
        }

        return operationMapper.selectByExampleWithBLOBs(example);
    }
}
