package com.jzx.bda.somero.mysql.service.impl;

import com.jzx.bda.somero.Annotation;
import com.jzx.bda.somero.BinaryAnnotation;
import com.jzx.bda.somero.Span;
import com.jzx.bda.somero.mysql.persistent.dao.AnnotationMapper;
import com.jzx.bda.somero.mysql.persistent.dao.SpanMapper;
import com.jzx.bda.somero.mysql.persistent.dao.TraceMapper;
import com.jzx.bda.somero.mysql.persistent.entity.Absannotation;
import com.jzx.bda.somero.mysql.persistent.entity.Trace;
import com.jzx.bda.somero.store.inter.InsertService;

public class InsertServiceImpl implements InsertService {

	@Override
	public void addSpan(Span span) {
		if (span.getServiceId() != null) {
			if (!Utils.isRoot(span) || Utils.isRoot(span) && Utils.isTopAnntation(span)) {
				spanMapper.addSpan(span);
			}
		}
	}

	@Override
	public void addTrace(Span span) {
		if (Utils.isTopAnntation(span) && Utils.isRoot(span)) {
			Annotation annotation = Utils.getCrAnnotation(span.getAnnotations());
			Annotation annotation1 = Utils.getCsAnnotation(span.getAnnotations());
			Trace t = new Trace();
			t.setTraceId(span.getTraceId());
			t.setDuration((int) (annotation.getTimestamp() - annotation1.getTimestamp()));
			t.setService(span.getServiceId());
			t.setTime(annotation1.getTimestamp());
			traceMapper.addTrace(t);
		}
	}

	@Override
	public void addAnnotation(Span span) {
		for (Annotation a : span.getAnnotations()) {
			Absannotation aa = new Absannotation(a, span);
			annotationMapper.addAnnotation(aa);
		}

		for (BinaryAnnotation b : span.getBinaryAnnotations()) {
			Absannotation bb = new Absannotation(b, span);
			annotationMapper.addAnnotation(bb);
		}
	}

	private AnnotationMapper annotationMapper;
	private SpanMapper spanMapper;
	private TraceMapper traceMapper;

	public void setAnnotationMapper(AnnotationMapper annotationMapper) {
		this.annotationMapper = annotationMapper;
	}

	public void setSpanMapper(SpanMapper spanMapper) {
		this.spanMapper = spanMapper;
	}

	public void setTraceMapper(TraceMapper traceMapper) {
		this.traceMapper = traceMapper;
	}
}
