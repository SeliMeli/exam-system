package com.thoughtworks.examsystem.service.impl;

import com.thoughtworks.examsystem.bean.GetPaperResponse;
import com.thoughtworks.examsystem.bean.ItemBean;
import com.thoughtworks.examsystem.dao.PaperDao;
import com.thoughtworks.examsystem.entity.Paper;
import com.thoughtworks.examsystem.service.GetPaperService;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : luoweiyao
 * @created : 2018/7/29
 * @project : exam-system
 * @package : com.thoughtworks.examsystem.service.impl
 */
@Service
public class GetPaperServiceImpl implements GetPaperService {
    @Resource
    private PaperDao paperDao;
    @Override
    public GetPaperResponse doService(long paperId) {
        Paper paper = paperDao.getOne(paperId);
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        GetPaperResponse getPaperResponse = new GetPaperResponse();
        List<ItemBean> itemBeanList = paper.getItems().stream()
                .map(item -> dozerBeanMapper.map(item, ItemBean.class))
                .sorted(Comparator.comparingLong(ItemBean::getId))
                .collect(Collectors.toList());
        getPaperResponse.setPaperName(paper.getName());
        getPaperResponse.setItemBeanList(itemBeanList);

        return getPaperResponse;
    }
}
